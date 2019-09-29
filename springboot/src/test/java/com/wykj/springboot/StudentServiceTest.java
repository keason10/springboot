package com.wykj.springboot;

import com.wykj.springboot.service.StudentManger.StudentManger;
import com.wykj.springboot.service.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * mockito  @Autowired 示例 文档
 * {@link "https://zhuanlan.zhihu.com/p/21444517"}
 *
 * 官方文档
 * {@link "https://static.javadoc.io/org.mockito/mockito-core/3.0.0/org/mockito/Mockito.html"}
 *
 */
public class StudentServiceTest extends MySpringBootBaseTest {
   // 对被测类中@Autowired的对象，用@Mocks标注；对被测类自己，用@InjectMocks标注。
    @Mock
    private StudentManger studentManger;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(studentManger.getDbStudentName(Mockito.anyString())).thenReturn(" throw new IllegalStateException(\"Db not exist\")");



    }

    @Test
    public void getDbStudentName() {
        //Mockito 被mock的 studentService 内部的，@Autowired的studentManger.getDbStudentName,由于没有实现数据库查询，所以需要mock
        System.out.println(studentService.getDbStudentName("11"));
    }


    @Test
    public void getServiceImplInnerNameTest() {
        //Mockito 被mock的 studentService 内部的方法，由于studentService.getServiceImplInnerName 目前是抛异常的，需要进行mock不抛异常。
        studentService = Mockito.spy(studentService);
        Mockito.doReturn("throw new IllegalStateException(\"该方法还没被实现\")").when(studentService).getServiceImplInnerName(Mockito.anyString());
        System.out.println(studentService.getServiceImplInnerName("11"));
    }
}
