//package storm;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSON;
//import com.zm.storm.domain.UserAccount;
//import com.zm.storm.service.IUserAccountService;
//
//@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
//@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
//
//public class TestMyBatis {
//    private static Logger logger = Logger.getLogger(TestMyBatis.class);
////  private ApplicationContext ac = null;
//    @Resource
//    private IUserAccountService userService = null;
//
////  @Before
////  public void before() {
////      ac = new ClassPathXmlApplicationContext("applicationContext.xml");
////      userService = (IUserService) ac.getBean("userService");
////  }
//
////    @Test
////    public void getUserAccount() {
////        UserAccount user = userService.getUserAccountById(1);
////        logger.info(JSON.toJSONString(user));
////    }
//
////    @Test
////    @Transactional
////    @Rollback(false)//在进行单元测试中，需要将回滚操作置为false
////    public void updateUserAccount() throws InterruptedException {
////    	System.out.println("开始更新");
////    	Thread.sleep(1000);
////    	userService.updateUserAccountById(1);
////    	UserAccount user1 = userService.getUserAccountById(1);
////    	logger.info(JSON.toJSONString(user1));
////    }
//
//  @Test
//  public void addUserAccount() {
//      UserAccount user = new UserAccount();
//      user.setUserName("•ิ.•ั๑ ๑๑ ♬✿");
//      user.setUserPwd("zm521521");
//      userService.addUserAccount(user);
//      logger.info(JSON.toJSONString(user));
//  }
//}
