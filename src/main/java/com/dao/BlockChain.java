package com.dao;

import com.util.Encrypt;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockChain {
    //存储区块链
    private List<Map<String,Object>> chain;
    //存储区块链的交易的列表
    private List<Map<String,Object>> currentTransactions;

    public List<Map<String, Object>> getChain() {
        return chain;
    }

    public void setChain(List<Map<String, Object>> chain) {
        this.chain = chain;
    }

    public List<Map<String, Object>> getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(List<Map<String, Object>> currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    //把该类设计为单例
    private static BlockChain blockChain = null;
    private BlockChain(){
        chain = new ArrayList<Map<String,Object>>();
        currentTransactions = new ArrayList<Map<String,Object>>();
        //创建创世区块
        newBlock(100,"0");
    }
    //创建单例对象
    public static BlockChain getInstance(){
        if (blockChain == null){
            blockChain = new BlockChain();
        }
        return blockChain;
    }

    //得到最后一个区块
    public Map<String,Object> lastBlock(){
        return getChain().get(getChain().size()-1);
    }

    //新增一个区块
    public Map<String,Object> newBlock(long proof,String previous_hash){
        Map<String,Object> block = new HashMap<String,Object>();
        block.put("index",getChain().size()+1);
        block.put("timestamp",System.currentTimeMillis());
        block.put("transactions",getCurrentTransactions());
        block.put("proof",proof);
        block.put("previous_hash",previous_hash);
        //重置当前的交易列表
        setCurrentTransactions(new ArrayList<Map<String, Object>>());
        getChain().add(block);
        return block;
    }

    //生成新交易信息，信息将加入到下一个待挖的区块中
    public int newTransactions(String sender,String recipient,long amount){
        Map<String, Object> transaction = new HashMap<String, Object>();
        transaction.put("sender", sender);
        transaction.put("recipient", recipient);
        transaction.put("amount", amount);
        getCurrentTransactions().add(transaction);
        return (Integer) lastBlock().get("index") + 1;
    }

    //生成区块的 SHA-256格式的 hash值
    public static  Object hash(Map<String,Object> block){
        return new Encrypt().getSHA256(new JSONObject(block).toString());
    }

    public long proofOfWork(long last_proof) {
        long proof = 0;
        while (!validProof(last_proof, proof)) {
            proof += 1;
        }
        return proof;
    }

    /**
     * 验证证明: 是否hash(last_proof, proof)以4个0开头?
     *
     * @param last_proof
     *            上一个块的证明
     * @param proof
     *            当前的证明
     * @return 以4个0开头返回true，否则返回false
     */
    public boolean validProof(long last_proof, long proof) {
        String guess = last_proof + "" + proof;
        String guess_hash = new Encrypt().getSHA256(guess);
        return guess_hash.startsWith("0000");
    }

    @Override
    public String toString() {
        return "BlockChain{" +
                "chain=" + chain +
                ", currentTransactions=" + currentTransactions +
                '}';
    }
}
