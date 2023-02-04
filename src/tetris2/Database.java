package tetris2;

import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class Database
{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/tetris_leaderboard";
    
    static Connection connect() throws SQLException
    {
        return DriverManager.getConnection( CONNECTION, "root", "password" );    
    }
    
    static boolean databaseExists()
    {
        try
        {
            Connection connection = connect();
            return true;
        }
        catch( SQLException e )
        {}
        return false;
    }
    
    static void createDatabase()
    {
        try
        {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate( "CREATE DATABASE tetris_leaderboard" );
        }
        catch( SQLException e ){}
    }
    
    static void createTable()
    {
        try
        {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate( "CREATE TABLE leaderboard(id INT PRIMARY KEY, name VARCHAR(20), level INT, score INT);" );
        }
        catch( SQLException e ){}
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
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard" );

            while ( resultSet.next() )
            {
                id = resultSet.getInt( "id" );
            }
        }
        catch( SQLException e ){}
        return id;
    }
    
    public static void insertPlayer( String name, int level, int score )
    {
        int id = getTopId() + 1;
        try
        {
        Connection connection = connect();
        Statement statement = connection.createStatement();
        statement.execute( "INSERT INTO `tetris_leaderboard`.`leaderboard`(`id`,`name`,`level`,`score`)VALUES("
                + id + "," + "\"" + name + "\"" + "," + level + "," + score + ");" );
        }
        catch( SQLException e ){}
    }
    
    public static void initTable( DefaultTableModel dtm )
    {
        try
        {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard" );

            while ( resultSet.next() )
            {
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( SQLException e ){}
    }
    
    public static void updateTable( DefaultTableModel dtm ) // display the most recent entry in the leaderboard
    {
        try
        {
            int id = getTopId();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM leaderboard WHERE id = " + id );

            while ( resultSet.next() )
            {
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( SQLException e ){}
    }
    
}
