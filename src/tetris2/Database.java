package tetris2;

import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class Database
{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/tetris_leaderboard";
    
    static boolean databaseExists()
    {
        boolean canConnect = false;
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            canConnect = true;
        }
        catch( SQLException e )
        {
            canConnect = false;
        }
        return canConnect;
    }
    
    static void createDatabase()
    {
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            statement.executeUpdate( "CREATE DATABASE tetris_leaderboard" );
        }
        catch( SQLException e )
        {
            e.getStackTrace();
        }
    }
    
    static void createTable()
    {
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            statement.executeUpdate( "CREATE TABLE leaderboard(id INT PRIMARY KEY, name VARCHAR(20), level INT, score INT);" );
        }
        catch( SQLException e )
        {
            e.getStackTrace();
        }
    }
    
    public static void fetchDatabase() // check if the leaderboard already exists, if not, create one
    {
        if( !databaseExists() ) 
        {
            createDatabase();
            createTable();
        }
    }
    
    public static int getTopId() // get the highest id in the current leaderboard
    {
        int id = 0;
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard" );

            while ( resultSet.next() )
            {
                id = resultSet.getInt( "id" );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        return id;
    }
    
    public static void insertPlayer( String name, int level, int score )
    {
        int id = getTopId() + 1;
        try
        {
        Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
        Statement statement = connection.createStatement();
        statement.execute( "INSERT INTO `tetris_leaderboard`.`leaderboard`(`id`,`name`,`level`,`score`)VALUES("
                + id + "," + "\"" + name + "\"" + "," + level + "," + score + ");" );
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
    
    public static void initTable( DefaultTableModel dtm )
    {
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard" );

            while ( resultSet.next() )
            {
                System.out.println( resultSet.getString( "name" ) + " " + resultSet.getInt( "level" ) + " " + resultSet.getInt( "score" ) );
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
    
    public static void updateTable( DefaultTableModel dtm ) // display the most recent entry in the leaderboard
    {
        try
        {
            int id = getTopId();
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard WHERE id = " + id );

            while ( resultSet.next() )
            {
                System.out.println( resultSet.getString( "name" ) + " " + resultSet.getInt( "level" ) + " " + resultSet.getInt( "score" ) );
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
    
}
