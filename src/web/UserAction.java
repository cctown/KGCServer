package web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import KGC.CommonFileManager;
import KGC.KGCDef;
import KGC.KGCModule;
import it.unisa.dia.gas.jpbc.Element;

@Controller
public class UserAction {
	
	@RequestMapping(value = "/getParams.htm")
	@ResponseBody
	public byte[] getParams(HttpServletRequest request) {
		String jsonString;
		Map<String, byte[]> resMap = new HashMap<String, byte[]>();
		
		try {
			//从默认路径获取公开参数
			byte[] params = CommonFileManager.getBytesFromFilepath(KGCDef.paramsPath);
			resMap.put("error_no", "0".getBytes());
			resMap.put("error_info", "成功获取公开参数".getBytes());
			resMap.put("params", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resMap.put("error_no", "-1".getBytes());
			resMap.put("error_info", "获取公开参数失败".getBytes());
			resMap.put("params", null);
		}
		
		jsonString = JSON.toJSONString(resMap);
		return jsonString.getBytes();
	}
	
	@RequestMapping(value = "/getPartKey.htm")
	@ResponseBody
	public byte[] getPartKey(HttpServletRequest request) {
		String id = request.getParameter("id");
		Element d;
		String jsonString;
		Map<String, byte[]> resMap = new HashMap<String, byte[]>();
		
		KGCModule module;
		try {
			module = new KGCModule();
			d = module.getPartKey(id);        //得到部分私钥
			resMap.put("error_no", "0".getBytes());
			resMap.put("error_info", "成功获取部分私钥".getBytes());
			resMap.put("partKey", d.toBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resMap.put("error_no", "-1".getBytes());
			resMap.put("error_info", "获取部分私钥失败".getBytes());
			resMap.put("partKey", null);
		}
		
		jsonString = JSON.toJSONString(resMap);
		return jsonString.getBytes();
	}
}
