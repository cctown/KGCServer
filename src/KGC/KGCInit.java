package KGC;

import SecretCloudProxy.PublicParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class KGCInit {
	public static void DefaultInit() {
		Pairing pairing;
		PublicParameters params;
		Element g;
		Element gps;
		Element s; // MASTER KEY
		
		pairing = PairingFactory.getPairing(KGCDef.propertiesPath);
		
		//选择一个随机的生成元g∈G1
		g = pairing.getG1().newRandomElement().getImmutable();
		//选择一个整数s ∈ Z∗p 作为主密钥
		s = pairing.getZr().newRandomElement().getImmutable();	 // master secret key
		try { 
			//保存主密钥到文件中
			CommonFileManager.saveBytesToFilepath(s.toBytes(), KGCDef.masterKeyPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("保存主密钥失败");
		}
		gps = g.duplicate().powZn(s).getImmutable(); 				// DDL hardness
		params = new PublicParameters(g, gps);  // g_s = g^s
		//保存公开参数
		try {
			CommonFileManager.writeObjectToFile(params, KGCDef.paramsPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("保存公开参数失败");
		}
	}
}
