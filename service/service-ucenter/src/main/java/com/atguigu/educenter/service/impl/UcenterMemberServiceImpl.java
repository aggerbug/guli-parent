package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.util.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhander.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate redisTemplate;
    private RedisAccessor redisAccessor;

    @Override
    public String loginUser(LoginVo member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember Member = baseMapper.selectOne(wrapper);

        if(Member==null){
            throw new GuliException(20001,"手机号不存在");
        }

        //md5
        if(!MD5.encrypt(password).equals(Member.getPassword())){
            throw new GuliException(20001,"密码错误");
        }

        if(Member.getIsDisabled()){
            throw new GuliException(20001,"账号已被禁用");
        }

        //生成token
        String token = JwtUtils.getJwtToken(Member.getId(), Member.getNickname());

        return token;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(
                StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册失败，请检查未输入项");
        }
        String redisMobileCode = (String)redisTemplate.opsForValue().get(mobile);
        if(!redisMobileCode.equals(code)){
            throw new GuliException(20001,"验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"该账号已被注册");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDeleted(false);
        member.setAvatar("https://atguigusun.oss-cn-beijing.aliyuncs.com/2021/03/15/6d552fa510be466f8b66acdd68b1739248393fc413958aa4ec45823eb4d69cbc.jpeg");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember selectOne = baseMapper.selectOne(wrapper);
        return selectOne;

    }
}
