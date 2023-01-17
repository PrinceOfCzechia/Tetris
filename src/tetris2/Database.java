package tetris2;

import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class Database
{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/tetris_leaderboard";
    
    public static int getTopId()
    {
        int id = 0;
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from leaderboard" );

            while ( resultSet.next() )
            {
                id = resultSet.getInt( "id" );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return id;
    }
    
    public static void insert( String name, int level, int score )
    {
        int id = getTopId() + 1;
        try
        {
        Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
        Statement statement = connection.createStatement();
        statement.execute( "INSERT INTO `tetris_leaderboard`.`leaderboard`(`id`,`name`,`level`,`score`)VALUES("
                + id + "," + "\"" + name + "\"" + "," + level + "," + score + ");" );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    static void initTable( DefaultTableModel dtm )
    {
        try
        {
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from leaderboard" );

            while ( resultSet.next() )
            {
                System.out.println( resultSet.getString( "name" ) + " " + resultSet.getInt( "level" ) + " " + resultSet.getInt( "score" ) );
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    static void updateTable( DefaultTableModel dtm )
    {
        try
        {
            int id = getTopId();
            Connection connection = DriverManager.getConnection( CONNECTION, "root", "password" );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from leaderboard where id = " + id );

            while ( resultSet.next() )
            {
                System.out.println( resultSet.getString( "name" ) + " " + resultSet.getInt( "level" ) + " " + resultSet.getInt( "score" ) );
                dtm.addRow( new Object[] { resultSet.getString( "name" ), resultSet.getInt( "level" ), resultSet.getInt( "score" ) } );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
