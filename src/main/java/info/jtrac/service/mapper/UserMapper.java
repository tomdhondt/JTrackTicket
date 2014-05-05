package main.java.info.jtrac.service.mapper;

import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserRole;
import main.java.info.jtrac.exception.mapper.MapperException;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.util.MappingUtil;

public class UserMapper extends AbstractMapper<User, UserDTO>{
	/**
	 * Method will map a DTO to a Object
	 * @return object as User
	 * @param dto as UserDTO 
	 */
	@Override
	public User mapToObject(UserDTO dto) throws MapperException{
		User object = null;
		if(null != dto){
			try{
			object = new User();
			object.setEmail(dto.getEmail());
			object.setId(MappingUtil.mapStringToLong(dto.getId()));
			object.setLocked(dto.isLocked());
			object.setLocale(dto.getLocale());
			object.setLoginName(dto.getLoginName());
			object.setName(dto.getName());
			object.setPassword(dto.getPassword());
			object.setType(MappingUtil.parseInt(dto.getType()));
			object.setActive(dto.isActive());
			object.setUserRole(UserRole.valueOf(dto.getUserRole()));
			//TODO implement the UserDTO properties
//			object.setUserSpaceRoles(userSpaceRoles);
//			object.setMetadata(metadata);
			}catch(Exception e){
				throw new MapperException("mapperException.object");
			}
		}
		return object;
	}
	/**
	 * Method will map a Object to a DTO
	 * @param object a User
	 * @return dto as UserDTO
	 */
	@Override
	public UserDTO mapToDTO(User object) throws MapperException{
		UserDTO dto = null;
		if(null != object){
			try{
			dto = new UserDTO();
			dto.setEmail(object.getEmail());
			dto.setId(String.valueOf(object.getId()));
			dto.setLocked(object.isLocked());
			dto.setLocale(object.getLocale());
			dto.setLoginName(object.getLoginName());
			dto.setName(object.getName());
			dto.setPassword(object.getPassword());
			dto.setType(Integer.toString(object.getType()));
			dto.setActive(object.isActive());
			dto.setUserRole(object.getUserRole().getRole());
			//TODO implement the UserDTO properties
//			object.setUserSpaceRoles(userSpaceRoles);
//			object.setMetadata(metadata);
			}catch(Exception e){
				throw new MapperException("mapperException.dto");
			}
		}
		return dto;
	}

}
