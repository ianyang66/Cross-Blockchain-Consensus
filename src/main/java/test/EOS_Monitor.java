package test;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

public class EOS_Monitor {
	public static void main(String []args) throws IOException {
		BasicConfigurator.configure();
		System.out.println("One Test");
		Test_Monitor();
		//get_producers();
	}
	public static void Test_Monitor() throws IOException{
		System.out.println("EOS Started ");
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8884/v1/chain/get_info"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion+"in EOS");
	}
	public static void get_producers() throws IOException{
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8884/v1/chain/get_producers"));
		Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
	}
}
