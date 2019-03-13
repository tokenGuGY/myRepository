package com.boot.security.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.security.server.model.PoSystem;

@Mapper
public interface PoSystemDao {

    @Select("select * from po_system t where t.system_id = #{id}")
    PoSystem getById(String id);

    @Delete("delete from po_system where system_id = #{id}")
    int delete(String id);

    int update(PoSystem poSystem);
    
    @Options(useGeneratedKeys = true, keyProperty = "systemId")
    @Insert("insert into po_system(system_id, system_name, system_code, sys_version, create_user, create_time, update_user, update_time, is_delete) values(#{systemId}, #{systemName}, #{systemCode}, #{sysVersion}, #{createUser}, #{createTime}, #{updateUser}, #{updateTime}, #{isDelete})")
    int save(PoSystem poSystem);
    
    int count(@Param("params") Map<String, Object> params);

    List<PoSystem> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
