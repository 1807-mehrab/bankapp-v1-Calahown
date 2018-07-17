package com.revature.bankingapp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class BAppDao {
	public ArrayList<Account> getCustAccounts(String accountNumber) {
		PreparedStatement ps = null;
		Account a = null;
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts Inner Join customer on accounts.c_id = customer.c_id where customer.c_id = (SELECT c_id FROM accounts where a_id = ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
				a = new Account(UUID.fromString(rs.getString("a_id")), rs.getDouble("balance"), rs.getInt("c_id"));
				} catch (IllegalArgumentException ex){
					ex.printStackTrace();
				}
				accounts.add(a);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return accounts;
	}
	
	public Account getAccount(String accountNumber) {
		PreparedStatement ps = null;
		Account a = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts where a_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					a = new Account(UUID.fromString(rs.getString("a_id")), rs.getDouble("balance"), rs.getInt("c_id"));
				} catch (IllegalArgumentException ex){
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return a;
	}
	
	public Customer getCustomer(int id, ArrayList<Account> accounts) {
		PreparedStatement ps = null;
		Customer c = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Select * FROM customer Where c_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				c = new Customer(id, rs.getString("c_name"), accounts);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	public int insertCustomer(Customer c) {
		PreparedStatement ps = null;
		int cid = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Insert Into Customer (passcode, c_name) values (?,?)";
			ps = conn.prepareStatement(sql, new String[]{"c_id"});
			ps.setString(1, c.getPassCode());
			ps.setString(2, c.getName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				cid = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(cid);
		return cid;
	}
	
	public void insertAccount(Account a) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			//string(UUID), balance, c_id
			String sql = "Insert Into Accounts Values (?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, a.getAccountNumber().toString());
			ps.setDouble(2, a.getBalance());
			ps.setInt(3, a.getCid());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateCustomer(Customer c) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Update Customer Set passcode = ?, c_name = ? where c_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, c.getPassCode());
			ps.setString(2, c.getName());
			ps.setInt(3, c.getCid());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateAccount(Account a) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Update Accounts Set balance = ? where a_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, a.getBalance());
			ps.setString(2, a.getAccountNumber().toString());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//lower acc1 balance, up account2 balance by number
	public void Transfer(String account1, String account2, Double number) {
		CallableStatement cs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "{CALL balancetransfer(?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, account1);
			cs.setString(2, account2);
			cs.setDouble(3, number);
			cs.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
