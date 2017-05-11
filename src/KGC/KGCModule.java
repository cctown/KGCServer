package KGC;

import SecretCloudProxy.CommonDef;
import SecretCloudProxy.CommonFileManager;
import SecretCloudProxy.PublicParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class KGCModule {
	private static Pairing pairing;
	private static Element s; // MASTER KEY
	
	public KGCModule() throws Exception {
		//从文件中读取参数初始化双线性群
		pairing = PairingFactory.getPairing(CommonDef.propertiesPath);
		//从默认路径获取主密钥
		byte[] sByte;
		try {
			sByte = CommonFileManager.getBytesFromFilepath(KGCDef.masterKeyPath);
			s = newZrElementFromBytes(sByte).getImmutable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("从" + KGCDef.masterKeyPath + "中获取主密钥失败");
			throw new Exception("获取主密钥失败");
		}
	}
	
	//	将byte[] b哈希到G1群
	public Element H1(byte[] b) {
		return pairing.getG1().newRandomElement().setFromHash(b, 0, b.length);
	}
	
	public Element newZrElementFromBytes(byte[] b) {
		return pairing.getZr().newElementFromBytes(b);
	}
	
	//具有身份IDA的用户A请求PKG进行部分私钥提取。 PKG计算gA = H1 (IDA ), DA = gAs 并将DA发送给用户A
	public Element getPartKey(String ID) {	
		Element gA = H1(ID.getBytes()).getImmutable();
		return gA.powZn(s);
	}
}
