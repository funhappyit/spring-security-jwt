package jpabook.start.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpabook.start.jwt.model.User;

//CRUD 함수를 JpaRepository가 들고 있음 
//@Repository라는 어노테이션이 없어도 IOC가 되요. 이유는 JpaRepository를 상속했기 때문에...
public interface UserRepository extends JpaRepository<User, Integer>{
	//findBy 규칙 -> Username 문법
	// select * from user where username = 1?
	public User findByUsername(String username);//JPA QUERY METHOD 
	
	//select * from user where Email = 1?
	//public User findByEmail();
}
