package storm;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.zm.storm.domain.Pa;
import com.zm.storm.domain.So;

public class PaSoTest {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		
		Pa pa = new Pa();
		So so = new So();
		
		pa.setPaName("Pa");
		pa.setPaAge(100);
		
		so.setSoName("So");
		so.setSoAge(10);
//		so.setPaName(pa.getPaName());
//		so.setPaAge(pa.getPaAge());
		
		System.out.println(pa.toString());
		System.err.println(so.toString()+":"+so.getPaName()+":"+so.getPaAge());
		BeanUtils.copyProperties(so, pa);
		System.err.println(so.toString()+":"+so.getPaName()+":"+so.getPaAge());

		
	}
}
