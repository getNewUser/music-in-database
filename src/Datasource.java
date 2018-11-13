import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final String DB_NAME = "music.db";

    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\80085\\IdeaProjects\\Databases\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME= "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";


    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final String COLUMN_SONG_ID = "_id";


    private Connection conn;

    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }catch(SQLException e){
            System.out.println("Couldnt connect to database: " + e.getMessage());
        }
        return false;
    }

    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println("Couldnt close connection: " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(){
      //  Statement statement = null;
     //   ResultSet results = null;


        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM  " + TABLE_ARTISTS)){


            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist();
                artist.setId(results.getInt(COLUMN_ARTIST_ID));
                artist.setName(results.getString(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Album> queryAlbums(String artistName){


        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_ALBUMS +
                                                            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " +
                                                            TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"" + artistName + "\";")){

            List<Album> albums = new ArrayList<>();
            while(results.next()){
                Album album = new Album();
                album.setId(results.getInt(COLUMN_ALBUM_ID));
                album.setName(results.getString(COLUMN_ALBUM_NAME));
                System.out.println(album.getName());
                album.setArtist(results.getInt(COLUMN_ALBUM_ARTIST));

                albums.add(album);
            }

            return albums;
        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Song> querySongs(String albumName){

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM
                                                            + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " WHERE " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " = \""+ albumName +"\";")){
            List<Song> songs = new ArrayList<>();

            while(results.next()){
                Song song = new Song();
                song.setTrack(results.getInt(COLUMN_SONG_TRACK));
                song.setTitle(results.getString(COLUMN_SONG_TITLE));
                song.setAlbum(results.getInt(COLUMN_SONG_ALBUM));

                songs.add(song);
            }

            return songs;
        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public void queryAddArtist(String artistName){
        System.out.println("INSERT INTO " + TABLE_ARTISTS + " (" + COLUMN_ARTIST_NAME + ") VALUES(\'" + artistName + "\')");
        try(Statement statement = conn.createStatement()){

            statement.executeUpdate("INSERT INTO " + TABLE_ARTISTS + " (" + COLUMN_ARTIST_NAME + ") VALUES(\'" + artistName + "\');");
            Artist artist = new Artist();
            artist.setName(artistName);

        }catch(SQLException e){
            System.out.println("queryAddArtist failed: " + e.getMessage());
        }

    }

    public void queryAddAlbum(String albumName, String artistName){
        System.out.println("INSERT INTO " + TABLE_ALBUMS +" (" + COLUMN_ALBUM_NAME +"," + COLUMN_ALBUM_ARTIST +") VALUES (\'" + albumName + "\', \'" + artistName +"\')");
        try(Statement statement = conn.createStatement()){
            List<Artist> artists = queryArtists();

            for(Artist artist : artists){
                if(artist.getName().equals(artistName)){
                    Album album = new Album();
                    album.setName(albumName);
                    album.setArtist(artist.getId());
                    statement.executeUpdate("INSERT INTO " + TABLE_ALBUMS +" (" + COLUMN_ALBUM_NAME +"," + COLUMN_ALBUM_ARTIST +") VALUES (\'" + albumName + "\', " + artist.getId() +")");
                }
            }


        }catch(SQLException e){
            System.out.println("addAlbum failed: " + e.getMessage());
        }
    }

    public void queryAddSong(String albumName, String songName,String artistName){
//        System.out.println("SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS + " INNER JOIN " +
//                TABLE_ARTISTS + " ON " + TABLE_ALBUMS +"." + COLUMN_ALBUM_ARTIST + " = " +TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
//                " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \'" + artistName + "\'");
        try(Statement statement = conn.createStatement()){

            List<Album> albums = queryAlbums(artistName);

            for(Album album : albums){
                if(album.getName().equals(albumName)){
                    Song song = new Song();
                    song.setTitle(songName);
                    song.setAlbum(album.getId());
//                    System.out.println("INSERT INTO " + TABLE_SONGS +" (" + COLUMN_SONG_TITLE + "," + COLUMN_SONG_ALBUM + ") VALUES (\'" + songName +"\', " + album.getId() + ");");
                    statement.executeUpdate("INSERT INTO " + TABLE_SONGS +" (" + COLUMN_SONG_TITLE + "," + COLUMN_SONG_ALBUM + ") VALUES (\'" + songName +"\', " + album.getId() + ");");
                }
            }



           // for(Album album : albums)

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void queryDeleteArtist(String artistName){
        System.out.println("DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = \'" + artistName + "\')");
        try(Statement statement = conn.createStatement()){
            statement.executeUpdate("DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = \'" + artistName + "\'");


        }catch(SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void queryDeleteAlbum(String albumName){
        System.out.println("DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = \'" + albumName + "\')");
        try(Statement statement = conn.createStatement()){
            statement.executeUpdate("DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = \'" + albumName + "\'");


        }catch(SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void queryDeleteSong(String songName){
        System.out.println("DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = \'" + songName + "\'");
        try(Statement statement = conn.createStatement()){
            statement.executeUpdate("DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONG_TITLE + " = \'" + songName + "\'");


        }catch(SQLException e){
            System.out.println("Something went wrongddd: " + e.getMessage());
        }
    }

    public void queryUpdateArtist(String artistName, String oldName){

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " FROM " + TABLE_ARTISTS + " WHERE " +
                    TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \'" + oldName + "\'")){


            int index = 0;
            while(results.next()){
                index = results.getInt(COLUMN_ARTIST_ID);
                System.out.println(index);
            }


            if(index < 1){
                System.out.println("Index less than 1");
                return;
            }

            statement.executeUpdate("UPDATE " + TABLE_ARTISTS + " SET " + COLUMN_ARTIST_NAME + " = \'" + artistName + "\' WHERE " + COLUMN_ARTIST_ID + " = " + index);

        }catch(SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void queryUpdateAlbum(String albumName, String oldName){

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + "," + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " FROM " + TABLE_ALBUMS + " WHERE " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " = \'" + oldName + "\';")){



            int index = 0;
            while(results.next()){
                index = results.getInt(COLUMN_ALBUM_ID);
                System.out.println(index);
            }


            if(index < 1){
                System.out.println("Index less than 1");
                return;
            }

            System.out.println("UPDATE " + TABLE_ALBUMS + " SET " + COLUMN_ARTIST_NAME + " = \'" + albumName + "\' WHERE " + COLUMN_ARTIST_ID + " = " + index +";");

            statement.executeUpdate("UPDATE " + TABLE_ALBUMS + " SET " + COLUMN_ARTIST_NAME + " = \'" + albumName + "\' WHERE " + COLUMN_ARTIST_ID + " = " + index +";");

        }catch(SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void queryUpdateSong(String songName, String oldName){

        System.out.println("SELECT " + TABLE_SONGS + "." + COLUMN_SONG_ID + " FROM " + TABLE_SONGS + " WHERE " +
                TABLE_SONGS + "." + COLUMN_SONG_TITLE + " = \'" + oldName + "\';");

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + TABLE_SONGS + "." + COLUMN_SONG_ID + " FROM " + TABLE_SONGS + " WHERE " +
                    TABLE_SONGS + "." + COLUMN_SONG_TITLE + " = \'" + oldName + "\';")){



            int index = 0;
            while(results.next()){
                index = results.getInt(COLUMN_SONG_ID);
                System.out.println(index);
            }


            if(index < 1){
                System.out.println("Index less than 1");
                return;
            }

            System.out.println("UPDATE " + TABLE_SONGS + " SET " + COLUMN_SONG_TITLE + " = \'" + songName + "\' WHERE " + COLUMN_SONG_ID + " = " + index +";");

            statement.executeUpdate("UPDATE " + TABLE_SONGS + " SET " + COLUMN_SONG_TITLE + " = \'" + songName + "\' WHERE " + COLUMN_SONG_ID + " = " + index +";");

        }catch(SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
















