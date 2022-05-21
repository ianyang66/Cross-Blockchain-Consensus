package test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

import javax.print.DocFlavor.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.protocol.websocket.events.NewHeadsNotification;
//import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import io.reactivex.Flowable;
import lab.Method;
//import org.web3j.tx.gas.StaticGasProvider;
public class Build {
	public static void main(String[] args) throws Exception {
		//String log4jConfPath = "C:\\Users\\88692\\eclipse-workspace\\java-ethereum\\src\\test\\resources\\log4j.properties";
		//PropertyConfigurator.configure(log4jConfPath);
		//BasicConfigurator.configure();
		//Ethereum1_Connect();
        //Ethereum2_Connect();
        //test_Deploy();
        //test_load();
        //Monitor_connect_webSocket_Ethereum1();
        //Monitor_connect_webSocket_Ethereum2();
		ContractTest();
	}
	public static void ContractTest() throws Exception {
		String walletPassword = "123456";
		String walletDirectory = "C:\\WorkSpace\\method\\Ethereum_1\\nodedata0\\keystore";
		String walletName = "UTC--2021-05-19T15-50-15.878066100Z--e2c0f1a4a41c798871d11e484dab1ad78b5a8bce";;
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		File file = new File(walletDirectory + "/" + walletName);
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword,walletDirectory + "/" + walletName);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword,file);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
		Method contract = Method.deploy(web3, credentials, provider).send();
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);

	}
	public static void Ethereum1_Connect() throws IOException {
		System.out.println("Ethereum 1 Started ");
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion+"in Ethereum_1");
	}
	public static void Ethereum2_Connect() throws IOException{
		System.out.println("Ethereum 2 Started ");
		 Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:7545"));
	     Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
	     String clientVersionX = web3ClientVersion.getWeb3ClientVersion();
	     System.out.println(clientVersionX+"in Ethereum_2");
	}
	public static void test_Deploy() throws Exception {
		System.out.println("Test_Deploy Start!");
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:7545"));
        Credentials credentials = Credentials.create("07cbd2cf5c3f51e39ce0c65d593a2c3207ba4a4a9356ddb1ae156bf8f7548a01");
        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
        Greeter contract = Greeter.deploy(
                web3, credentials,provider, "test"
        ).send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contractAddress);
        System.out.println("Test_Deploy Done!");
	}
	public static void test_load() throws Exception  {
		String node_Add ="0xA09Fa02280dc68CC68aE52b989aDFEb274B04280";
		String node_key = "37a43d9f22c19c657a728949aa4bb76b6fe225e15fa9231c7bce1bbf15275638";
		String Contract_Add = "0xb2f93FA85E241e8b40AE20A7A11E236E60205D77";
		
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:7545"));
		//credential = Private key about Account node
        Credentials credentials = Credentials.create(node_key);
        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
        //Load(Contract Address,web3,crendentials,provider)
        Greeter contract = Greeter.load(Contract_Add, web3,
                credentials, provider);
        //System.out.println(contract.greet().send());
        TransactionReceipt transactionReceipt = contract.newGreeting("Cry out").send();
		System.out.println(contract.greet().send());
		//test_connect_webSocket();

		//EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, node_Add);
        //contract.modifiedEventFlowable(filter).subscribe(log -> System.out.println(log.newGreeting));
	}
	public static void Monitor_connect_webSocket_Ethereum1() throws ConnectException, URISyntaxException {
		String node_Add ="0x1AB8a6ABC9B0156d2ce3917Ef94E09f589502FB4";
		System.out.println("Start test webSocket");
		// websocket
        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:7545"));
        boolean includeRawResponses = false;
        WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
        // 注意該程式碼在官方文檔沒說，但必须要加上，否則會出現 WebsocketNotConnectedException。
        webSocketService.connect();
        Web3j web3 = Web3j.build(webSocketService);
        Flowable<NewHeadsNotification> notifications = web3.newHeadsNotifications();
        notifications.subscribe(event -> {
            // get block hash
            System.out.println(event.getParams().getResult().getHash());
        });
        web3.logsNotifications(Arrays.asList(node_Add), Collections.emptyList()).subscribe(log -> {
            // get transaction hash
        	System.out.println("OutPut Ethereum1_Monitor webSocket: ");
            System.out.println("TransactionHash from Ethereum1: "+log.getParams().getResult().getTransactionHash());
        });
	}
	public static void Monitor_connect_webSocket_Ethereum2() throws ConnectException, URISyntaxException {
		String node_Add ="0x1AB8a6ABC9B0156d2ce3917Ef94E09f589502FB4";
		System.out.println("Start test webSocket");
		// websocket
        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:8545"));
        boolean includeRawResponses = false;
        WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
        // 注意該程式碼在官方文檔沒說，但必须要加上，否則會出現 WebsocketNotConnectedException。
        webSocketService.connect();
        Web3j web3 = Web3j.build(webSocketService);
        Flowable<NewHeadsNotification> notifications = web3.newHeadsNotifications();
        notifications.subscribe(event -> {
            // get block hash
            System.out.println(event.getParams().getResult().getHash());
        });
        web3.logsNotifications(Arrays.asList(node_Add), Collections.emptyList()).subscribe(log -> {
            // get transaction hash
        	System.out.println("OutPut Ethereum2_Monitor webSocket: ");
            System.out.println("TransactionHash from Ethereum2: "+log.getParams().getResult().getTransactionHash());
        });
	}
}

