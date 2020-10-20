package com.lmac.simpbot.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lmac.simpbot.data.VibinMember;

public class DAO {

	public DAO() {

	}

	public static void createNewDatabase(String dbName) {

		String url = "jdbc:sqlite:data/" + dbName + ".db";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");

				String sql = "CREATE TABLE IF NOT EXISTS members (\n" + "id long PRIMARY KEY,\n"
						+ "name text NOT NULL,\n" + "gender int,\n" + "simpRating float\n" + ");";

				Statement stmt = conn.createStatement();
				// create a new table
				stmt.execute(sql);

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static VibinMember addMember(String dbName, String memberName, long id) {

		String url = "jdbc:sqlite:data/" + dbName + ".db";

		String sql = "INSERT INTO members(id,name,gender,simpRating) VALUES(?,?,?,?)";
		VibinMember vm = null;

		try (Connection conn = DriverManager.getConnection(url)) {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setString(2, memberName);
			pstmt.setInt(3, 0);
			pstmt.setFloat(4, 0.0f);
			pstmt.executeUpdate();
			vm = new VibinMember(memberName, id, 0f, 0);
		} catch (SQLException e) {
			System.out.println("ADD MEMBER  ERROR " + e.getMessage());
		}

		return vm;

	}

	public static VibinMember GetMemberByID(String dbName, long id) {
		VibinMember vm = null;
		String url = "jdbc:sqlite:data/" + dbName + ".db";
		String sql = "SELECT name, gender, simpRating FROM members WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(url)) {

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				vm = new VibinMember(rs.getString("name"), id, rs.getFloat("simpRating"), rs.getInt("gender"));
			}

		} catch (SQLException e) {
			System.out.println("GET MEMBER BY ID ERROR " + e.getMessage());
		}

		return vm;
	}

	public static List<VibinMember> GetMembersFromServer(String dbName) {
		System.out.println("GETTING MEMBER FROM SERVER!");

		String url = "jdbc:sqlite:data/" + dbName + ".db";
		String sql = "SELECT id, name, gender, simpRating FROM members";
		List<VibinMember> members = new ArrayList<VibinMember>();

		try (Connection conn = DriverManager.getConnection(url)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				VibinMember vm = new VibinMember(rs.getString("name"), rs.getLong("id"), rs.getFloat("simpRating"),
						rs.getInt("gender"));

				System.out.println(vm);
				members.add(vm);
			}
		} catch (SQLException e) {
			System.out.println("GET MEMBER FROM SERVER " + e.getMessage());
		}

		return members;

	}

	public static boolean databaseExists(String dbName) {
		File file = new File("data/" + dbName + ".db");

		if (file.exists()) {
			System.out.println("EXISTS!");
			return true;
		} else {
			System.out.println("DOESNT EXIST!");
			return false;
		}

	}

}
