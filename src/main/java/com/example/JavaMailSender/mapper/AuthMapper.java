package com.example.JavaMailSender.mapper;

import com.example.JavaMailSender.dto.request.RegisterRequest;
import com.example.JavaMailSender.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    User userFromRequest(RegisterRequest request);
}