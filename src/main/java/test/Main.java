package test;
import static org.junit.Assert.*;
import java.util.Date;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;

public class Main {
	public static List<Block> blockchain = new ArrayList<Block>();
    public static int prefix = 4;
    public static String prefixString = new String(new char[prefix]).replace('\0', '0');
    
    public static void main(String[] args) throws IOException {
    	Ethereum_build();
        Ethereum2_bulid();
    	//setUpRelay();
    	//givenBlockchain_whenNewBlockAdded_thenSuccess(); //add new Block
    	//givenBlockchain_whenValidated_thenSuccess();//check block with hash
    	//blockchain.clear();
    }

    //@BeforeClass
    public static void setUpRelay() {
    	System.out.println("Start SetUp!");
        
    	Block genesisBlock = new Block("The is the Genesis Block.", "0", new Date().getTime());
        genesisBlock.mineBlock(prefix);
        blockchain.add(genesisBlock);
        
        show_state(genesisBlock);
        
        Block firstBlock = new Block("The is the First Block.", genesisBlock.getHash(), new Date().getTime());
        firstBlock.mineBlock(prefix);
        blockchain.add(firstBlock);
        
        show_state(firstBlock);
    }

    //@Test
    public static void givenBlockchain_whenNewBlockAdded_thenSuccess() {
    	System.out.println("Start Add new Block");
        Block newBlock = new Block("The is a New Block.", blockchain.get(blockchain.size() - 1)
            .getHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        assertTrue(newBlock.getHash()
            .substring(0, prefix)
            .equals(prefixString));
        blockchain.add(newBlock);
        
        show_state(newBlock);
    }

    //@Test
    public static void givenBlockchain_whenValidated_thenSuccess() {
    	System.out.println("Start Validated");
        boolean flag = true;
        for (int i = 0; i < blockchain.size(); i++) {
            String previousHash = i == 0 ? "0"
                : blockchain.get(i - 1)
                    .getHash();
            flag = blockchain.get(i)
                .getHash()
                .equals(blockchain.get(i)
                    .calculateBlockHash()) //check block hash
                && previousHash.equals(blockchain.get(i) //check PreviousHash
                    .getPreviousHash())
                && blockchain.get(i)//check sequence
                    .getHash()
                    .substring(0, prefix)
                    .equals(prefixString);
            if (!flag)
                break;
        }
        assertTrue(flag);
        System.out.println("assertTrue : " + String.valueOf(flag));
    }

    //@AfterClass
    public static void tearDown() {
        blockchain.clear();
    }
    public static void show_state(Block block) {
    	System.out.println(block.getData());
    	System.out.println("Amount of block : " + blockchain.size());
    	System.out.println("BlockChain : " + blockchain.toString());
    }
    public static void Ethereum_build() throws IOException {
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
	public static void Quorum_build() throws IOException{
		//Connect Port 
		Quorum quorum = Quorum.build(new HttpService("http://localhost:7545"));
		Web3ClientVersion web3ClientVersion = quorum.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
	}
	public static void test_contract() throws IOException{
		
	}
}	
