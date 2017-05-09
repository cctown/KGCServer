//package KGC;
//
//import it.unisa.dia.gas.jpbc.Element;
//
//public class Encrypt {
//	String IDA = "小花";
//	String IDB = "老朽";
//	String testS = "测试数据：安全已经被认为";
//	MasterGen master;
//	skpkPair keyA;
//	skpkPair keyB;
//	Element t;
//	private static String paramsPath = "/Users/chencaixia/SecretCloud/params/";
//	
//	Encrypt() {
//		master = new MasterGen();
//		
//		keyA = skpkGen(IDA);
//		keyB = skpkGen(IDB);
//		t = MasterGen.pairing.getGT().newRandomElement().getImmutable();
//		
//		Ciphertext cipher = encryptMsg(testS.getBytes(), keyB.pk, t);
//		byte[] m = decryptMsg(cipher, keyB.sk);
//		String msg = new String(m);
//		System.out.println("解密出原文为：" + msg);
//		
//		ShareCipher shareCipher = encryptShareMsg(testS.getBytes(), keyA.pk, t);
//		ReencryptionKey reKey = rkGen(keyA.sk, keyB.pk, shareCipher.grt, t);
//		ReencryptionCipher reCipher = reencryptMsg(shareCipher, reKey);
//		byte[] m2 = decryptShareMsg(reCipher, keyB.sk);
//		String msg2 = new String(m2);
//		System.out.println("重加密解密出原文为：" + msg2);
//	}
//	
//	//生成密文(grt, m * e(gA^r, g^s^xAt))
//	private Ciphertext encryptMsg(byte[] msg, PublicKey pk, Element t) {
//		//随机地选择整数r
//		Element r = MasterGen.pairing.getGT().newRandomElement().getImmutable();
//		Element g = master.newG1ElementFromBytes(MasterGen.params.g).getImmutable();
//		Element gr = g.duplicate().powZn(r).getImmutable();
//		Element grt = gr.duplicate().powZn(t).getImmutable();
//		Element gAr = pk.gA.duplicate().powZn(r).getImmutable();
//		Element gsxAt = pk.gsxA.duplicate().powZn(t).getImmutable();
//		Element eg = master.e(gAr, gsxAt).getImmutable();
//		Element m = master.newGTElementFromBytes(msg).getImmutable();
//		Element me = m.duplicate().mul(eg.duplicate()).getImmutable();
//		Ciphertext cipher = new Ciphertext(grt, me.toBytes());
//		return cipher;
//	}
//	
//	//解密信息m ：m * e(gA^r, g^s^xAt) / e(gA^s^xA, g^rt) = cipher.me/e(skA, g^rt)
//	private byte[] decryptMsg(Ciphertext cipher, Element sk) {
//		Element grt = cipher.grt.duplicate();
//		Element eskgrt = master.e(sk, grt);
//		Element me = master.newGTElementFromBytes(cipher.me).getImmutable();
//		Element m = me.duplicate().div(eskgrt).duplicate().getImmutable();
//		return m.toBytes();
//	}
//	
//	//生成分享密文
//	//c' = C'A(m) = (g^rt，m * e(gA^r，g^s^xAt)) = (g^rt，m * e(pkA.gA^r，pkA.gsxA^t))
//	public ShareCipher encryptShareMsg(byte[] msg, PublicKey pk, Element t) {
//		Ciphertext cipher = encryptMsg(msg, pk, t);
//		ShareCipher shareCipher= new ShareCipher(cipher.grt, cipher.me);
//		return shareCipher;
//	}
//	
//	//代理重加密
//	//c'' = m * e(gA^r，g^s·xAt) * e(gA^-s·xA · H2^t (x)，g^rt) = shareCipher.cipher * e(rk.gH, shareCipher.grt)
//	public ReencryptionCipher reencryptMsg(ShareCipher shareCipher, ReencryptionKey rk) {
//		Element m = master.newGTElementFromBytes(shareCipher.cipher).getImmutable();
//		Element gH = rk.gH.duplicate().getImmutable();
//		Element gr = shareCipher.grt.getImmutable();
//		Element egg = master.e(gH, gr).getImmutable();
//		Element me = m.duplicate().mul(egg).duplicate().getImmutable();
//		ReencryptionCipher cipher = new ReencryptionCipher(me.toBytes(), rk.CBx, rk.CBgrt2);
//		return cipher;
//	}
//	
//	//代理重加密密文解密
//	//c'' / e(H2(x)，g^rt2)
//	public byte[] decryptShareMsg(ReencryptionCipher cipher, Element skB) {
//		//解密 CB(x，grt2)得到 x 和 grt2
//		Element x = master.newG1ElementFromBytes(decryptMsg(cipher.CBx, skB)).getImmutable();
//		Element grt2 = master.newG1ElementFromBytes(decryptMsg(cipher.CBgrt2, skB)).getImmutable();
//		Element H2x = master.H2(x).getImmutable();
//		Element eHg = master.e(H2x, grt2);
//		Element c = master.newGTElementFromBytes(cipher.reCipher);
//		Element m = c.duplicate().div(eHg).duplicate().getImmutable();
//		return m.toBytes();
//	}
//	
//	//生成重加密密钥
//	//rkA→B = (gA^(-s·xA) * H2(x)^t，CB(x), CB(grt2)) = skA^(-1) * H2(x)^t, CB(x), CB(grt2))
//	public ReencryptionKey rkGen(Element skA, PublicKey pkB, Element grt, Element t) {
//		//随机的选择x ∈ GT
//		Element x = MasterGen.pairing.getGT().newRandomElement().getImmutable();
//		Element skA1 = skA.duplicate().invert().getImmutable();
//		Element H2x = master.H2(x).getImmutable();
//		Element H2xt = H2x.duplicate().powZn(t).getImmutable();
//		Element skH2 = skA1.duplicate().mul(H2xt).getImmutable();
//		 
//		Ciphertext CBx = encryptMsg(x.toBytes(), pkB, t);
//		Element grt2 = grt.duplicate().powZn(t).getImmutable();
//		Ciphertext CBgrt2 = encryptMsg(grt2.toBytes(), pkB, t);
//		ReencryptionKey key= new ReencryptionKey(skH2, CBx, CBgrt2);
//		
//		return key;
//	}
//	
//	 //生成公私钥对
//	 public skpkPair skpkGen(String ID) {
//		 //随机选择一个整数xA ∈ Z∗p
//		 Element xA = MasterGen.pairing.getGT().newRandomElement().getImmutable();
//		//私钥 skA = gA^s^xA
//		 Element dA = master.getPartKey(ID);
//		 CommonFileManager.writeObjectToFile(dA.toBytes(), paramsPath + ID + "_partKey.dat");
//		 Element sk = dA.duplicate().powZn(xA);
//		//公钥 pkA = (gA, g^s^xA)), 其中gA= H1(IDA)
//		 Element gA = master.H1(ID.getBytes()).getImmutable();	
//		 Element gps = master.newG1ElementFromBytes(MasterGen.params.gps).getImmutable();
//		 Element gsxA = gps.duplicate().powZn(xA);
//		 PublicKey pk = new PublicKey(gA, gsxA);
//		 skpkPair key = new skpkPair(sk, pk);
//		 
//		 return key;
//	 }
//}
