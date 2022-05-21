package test;

import java.io.IOException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
public class test {
	public static void main(String[] args) throws IOException {
        Ethereum1_build();
        Ethereum2_bulid();
 
	}
	public static void Ethereum1_build() throws IOException {
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion+"in Ethereum_1");
	}
	public static void Ethereum2_bulid() throws IOException{
		 Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:7545"));
	     Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
	     String clientVersionX = web3ClientVersion.getWeb3ClientVersion();
	     System.out.println(clientVersionX+"in Ethereum_2");
	}
	
}
