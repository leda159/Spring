package org.bigdata.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
public class MemberTests {

	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;

	//인증을 위한 사용자 테이블에 데이터 insert
	
	@Ignore
	public void testInsertMember() {
		
		String sql = "insert into tbl_member(userid,userpw,username) values(?,?,?)";
		
		for(int i=0;i<100;i++) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				
				con = ds.getConnection();
				
				pstmt = con.prepareStatement(sql);
				
				//비밀번호는 암호화 처리
				pstmt.setString(2,pwencoder.encode("pw" + i));
				
				//user0 ~ user79는 일반사용자
				//user80 ~ user89는 운영자
				//user90 ~ user99는 관리자
				if(i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(3, "일반사용자" + i);
				}else if(i < 90) {
					pstmt.setString(1, "manager" + i);
					pstmt.setString(3, "운영자" + i);
				}else {
					pstmt.setString(1, "admin" + i);
					pstmt.setString(3, "관리자" + i);
				}
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//사용자에 대한 권한을 insert
	@Test
	public void testInsertAuth() {
		
		String sql = "insert into tbl_member_auth(userid,auth) values(?,?)";
		
		for(int i=0;i<100;i++) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				
				con = ds.getConnection();
				
				pstmt = con.prepareStatement(sql);
				
				//user0 ~ user79
				if(i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER");
				}else if(i < 90) {//user80~user89
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				}else {//user90 ~ user99
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}





