import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
/*
 * Created by JFormDesigner on Thu Oct 18 22:56:56 PDT 2018
 */



/**
 * @author Vilmantas
 */
public class MainWindow extends JFrame {
	Datasource datasource = new Datasource();
	public MainWindow() {
		initComponents();

		datasource.open();

        List<Artist> artists = datasource.queryArtists();

        if (artists == null) {
            System.out.println("no artists");
            return;
        }


//        artistsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        artistsList.setLayoutOrientation(JList.VERTICAL);
        DefaultListModel DLM = new DefaultListModel();
        for(Artist artist : artists){
            DLM.addElement(artist.getName());
        }
        artistsList.setModel(DLM);

        datasource.close();

	}

    private void listValueChanged(ListSelectionEvent e) {
	    DefaultListModel clear = new DefaultListModel();
	    clear.removeAllElements();
	    songList.setModel(clear);

        datasource.open();
        List<Album> albums = datasource.queryAlbums(artistsList.getSelectedValue().toString());

//        for(Album album : albums){
//           System.out.println(album.getName());
//       }
        DefaultListModel DLM = new DefaultListModel();

        for(Album album : albums){
            DLM.addElement(album.getName());
        }
        albumsList.setModel(DLM);


        datasource.close();

    }

    private void artistsListValueChanged(ListSelectionEvent e) {
        // TODO add your code here
    }

    private void albumsListValueChanged(ListSelectionEvent e) {
        datasource.open();
        List<Song> songs = datasource.querySongs(albumsList.getSelectedValue().toString());

        DefaultListModel DLM = new DefaultListModel();
        for(Song song : songs){
            DLM.addElement(song.getTitle());
        }
        songList.setModel(DLM);
        datasource.close();
    }

    private void addArtistButtonMouseClicked(MouseEvent e) {
        addArtistDialog.setVisible(true);
    }


    private void addArtistButton2MouseClicked(MouseEvent e) {
        datasource.open();

        datasource.queryAddArtist(addArtistTextField.getText());
        List<Artist> artists = datasource.queryArtists();

        if (artists == null) {
            System.out.println("no artists");
            return;
        }
        DefaultListModel DLM = new DefaultListModel();
        for(Artist artist : artists){
            DLM.addElement(artist.getName());
        }
        artistsList.setModel(DLM);
        addArtistDialog.dispose();

        datasource.close();
    }

    private void addAlbumAddButtonMouseClicked(MouseEvent e) {
	    String albumName = addAlbumAlbumNameTextField.getText();
	    String artistName = artistsList.getSelectedValue().toString();
        System.out.println("Artist: " + artistsList.getSelectedValue().toString());
	    datasource.open();

	    datasource.queryAddAlbum(albumName, artistName);
	    List<Album> albums = datasource.queryAlbums(artistName);
	    DefaultListModel DLM = new DefaultListModel();

	    for(Album album : albums){
	        DLM.addElement(album.getName());
        }

        albumsList.setModel(DLM);
	    datasource.close();
	    addAlbumDialog.dispose();
    }

    private void addSongAddButtonMouseClicked(MouseEvent e) {
        String albumName = albumsList.getSelectedValue().toString();
        String songName = addSongTextField.getText();
        String artistName = artistsList.getSelectedValue().toString();

        System.out.println(albumName + " + " + songName);
        datasource.open();

        datasource.queryAddSong(albumName, songName, artistName);

        List<Song> songs = datasource.querySongs(albumName);
        DefaultListModel DLM = new DefaultListModel();

        for(Song song : songs){
            DLM.addElement(song.getTitle());
        }

        songList.setModel(DLM);
        datasource.close();
        addSongDialog.dispose();
    }



    private void removeArtistButtonMouseClicked(MouseEvent e) {
	    String artistName = artistsList.getSelectedValue().toString();

	    datasource.open();
	    datasource.queryDeleteArtist(artistName);
        List<Artist> artists = datasource.queryArtists();

        if (artists == null) {
            System.out.println("no artists");
            return;
        }


//        artistsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        artistsList.setLayoutOrientation(JList.VERTICAL);
        DefaultListModel DLM = new DefaultListModel();
        for(Artist artist : artists){
            DLM.addElement(artist.getName());
        }
        artistsList.setModel(DLM);

        datasource.close();
    }

    private void removeAlbumButtonMouseClicked(MouseEvent e) {
        String albumName = albumsList.getSelectedValue().toString();
        String artistName = artistsList.getSelectedValue().toString();

        datasource.open();
        datasource.queryDeleteAlbum(albumName);
        List<Album> albums = datasource.queryAlbums(artistName);

        if (albums == null) {
            System.out.println("no artists");
            return;
        }


//        artistsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        artistsList.setLayoutOrientation(JList.VERTICAL);
        DefaultListModel DLM = new DefaultListModel();
        for(Album album: albums){
            DLM.addElement(album.getName());
        }
        albumsList.setModel(DLM);

        datasource.close();
    }

    private void removeSongButtonMouseClicked(MouseEvent e) {
        String albumName = albumsList.getSelectedValue().toString();
        String artistName = artistsList.getSelectedValue().toString();
        String songName = songList.getSelectedValue().toString();

        datasource.open();
        datasource.queryDeleteSong(songName);
        List<Song> songs = datasource.querySongs(albumName);

        if (songs == null) {
            System.out.println("no artists");
            return;
        }


//        artistsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        artistsList.setLayoutOrientation(JList.VERTICAL);
        DefaultListModel DLM = new DefaultListModel();
        for(Song song: songs){
            DLM.addElement(song.getTitle());
            System.out.println(song.getTitle());
        }
        songList.setModel(DLM);

        datasource.close();
    }


    private void updateArtistMouseClicked(MouseEvent e) {
        String artistName = updateArtistTextfield.getText();
        String oldName = artistsList.getSelectedValue().toString();
        datasource.open();
        datasource.queryUpdateArtist(artistName, oldName);

        List<Artist> artists = datasource.queryArtists();

        DefaultListModel DLM = new DefaultListModel();
        for(Artist artist : artists){
            DLM.addElement(artist.getName());
        }
        artistsList.setModel(DLM);

        updateArtistDialog.dispose();

        datasource.close();

    }

    private void updateAlbumMouseClicked(MouseEvent e) {
        String albumName = updateAlbumTextfield.getText();
        String oldName = albumsList.getSelectedValue().toString();
        datasource.open();
        datasource.queryUpdateAlbum(albumName, oldName);

        List<Album> albums = datasource.queryAlbums(artistsList.getSelectedValue().toString());

        DefaultListModel DLM = new DefaultListModel();
        for(Album album : albums){
            DLM.addElement(album.getName());
        }
        albumsList.setModel(DLM);

        updateArtistDialog.dispose();

        datasource.close();
    }

    private void updateSongMouseClicked(MouseEvent e) {
        String songName = updateSongTextfield.getText();
        String oldName = songList.getSelectedValue().toString();
        datasource.open();
        datasource.queryUpdateSong(songName, oldName);

        List<Song> songs = datasource.querySongs(albumsList.getSelectedValue().toString());

        DefaultListModel DLM = new DefaultListModel();
        for(Song song : songs){
            DLM.addElement(song.getTitle());
        }
        songList.setModel(DLM);

        updateSongDialog.dispose();

        datasource.close();
    }






    private void addArtistFramePropertyChange(PropertyChangeEvent e) {
        // TODO add your code here
    }
    private void addArtistCloseButtonMouseClicked(MouseEvent e) {
        addArtistDialog.dispose();
    }
    private void addAlbumCloseButtonMouseClicked(MouseEvent e) {
        addAlbumDialog.dispose();
    }
    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void addAlbumOpenDialogMouseClicked(MouseEvent e) {
        addAlbumDialog.setVisible(true);
    }

    private void addSongButtonMouseClicked(MouseEvent e) {
        addSongDialog.setVisible(true);
    }

    private void removeArtistMouseClicked(MouseEvent e) {

        //  datasource.open();
    }

    private void artistUpdateDialogButtonMouseClicked(MouseEvent e) {
        updateArtistDialog.setVisible(true);
    }

    private void albumsUpdateDialogButtonMouseClicked(MouseEvent e) {
        updateAlbumDialog.setVisible(true);
    }

    private void songsUpdateDialogButtonMouseClicked(MouseEvent e) {
        updateSongDialog.setVisible(true);
    }

    private void button8MouseClicked(MouseEvent e) {
        updateArtistDialog.dispose();
    }

    private void button10MouseClicked(MouseEvent e) {
        updateAlbumDialog.dispose();
    }

    private void button12MouseClicked(MouseEvent e) {
        updateSongDialog.dispose();
    }











	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vilmantas
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        artistsList = new JList();
        scrollPane2 = new JScrollPane();
        albumsList = new JList();
        scrollPane3 = new JScrollPane();
        songList = new JList();
        label2 = new JLabel();
        label3 = new JLabel();
        addArtistButton = new JButton();
        addAlbumOpenDialog = new JButton();
        addSongButton = new JButton();
        removeArtistButton = new JButton();
        artistUpdateDialogButton = new JButton();
        removeAlbumButton = new JButton();
        albumsUpdateDialogButton = new JButton();
        removeSongButton = new JButton();
        songsUpdateDialogButton = new JButton();
        addArtistDialog = new JDialog();
        addArtistTextField = new JTextField();
        label4 = new JLabel();
        addArtistButton2 = new JButton();
        addArtistCloseButton = new JButton();
        addAlbumDialog = new JDialog();
        label5 = new JLabel();
        addAlbumAlbumNameTextField = new JTextField();
        addAlbumAddButton = new JButton();
        addAlbumCloseButton = new JButton();
        addSongDialog = new JDialog();
        addSongTextField = new JTextField();
        label6 = new JLabel();
        addSongAddButton = new JButton();
        addSongCloseButton = new JButton();
        updateArtistDialog = new JDialog();
        label7 = new JLabel();
        updateArtistTextfield = new JTextField();
        updateArtist = new JButton();
        button8 = new JButton();
        updateAlbumDialog = new JDialog();
        label8 = new JLabel();
        updateAlbumTextfield = new JTextField();
        updateAlbum = new JButton();
        button10 = new JButton();
        updateSongDialog = new JDialog();
        label9 = new JLabel();
        updateSongTextfield = new JTextField();
        updateSong = new JButton();
        button12 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Select artist :");

        //======== scrollPane1 ========
        {

            //---- artistsList ----
            artistsList.addListSelectionListener(e -> {
			listValueChanged(e);
			artistsListValueChanged(e);
		});
            scrollPane1.setViewportView(artistsList);
        }

        //======== scrollPane2 ========
        {

            //---- albumsList ----
            albumsList.addListSelectionListener(e -> albumsListValueChanged(e));
            scrollPane2.setViewportView(albumsList);
        }

        //======== scrollPane3 ========
        {
            scrollPane3.setViewportView(songList);
        }

        //---- label2 ----
        label2.setText("Albums:");

        //---- label3 ----
        label3.setText("Songs:");

        //---- addArtistButton ----
        addArtistButton.setText("Add artist");
        addArtistButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addArtistButtonMouseClicked(e);
            }
        });

        //---- addAlbumOpenDialog ----
        addAlbumOpenDialog.setText("Add album");
        addAlbumOpenDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addAlbumOpenDialogMouseClicked(e);
            }
        });

        //---- addSongButton ----
        addSongButton.setText("Add song");
        addSongButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addSongButtonMouseClicked(e);
            }
        });

        //---- removeArtistButton ----
        removeArtistButton.setText("Remove artist");
        removeArtistButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeArtistButtonMouseClicked(e);
            }
        });

        //---- artistUpdateDialogButton ----
        artistUpdateDialogButton.setText("Update artist");
        artistUpdateDialogButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                artistUpdateDialogButtonMouseClicked(e);
            }
        });

        //---- removeAlbumButton ----
        removeAlbumButton.setText("Remove album");
        removeAlbumButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeAlbumButtonMouseClicked(e);
            }
        });

        //---- albumsUpdateDialogButton ----
        albumsUpdateDialogButton.setText("Update artist");
        albumsUpdateDialogButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                albumsUpdateDialogButtonMouseClicked(e);
            }
        });

        //---- removeSongButton ----
        removeSongButton.setText("Remove song");
        removeSongButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeSongButtonMouseClicked(e);
            }
        });

        //---- songsUpdateDialogButton ----
        songsUpdateDialogButton.setText("Update song");
        songsUpdateDialogButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                songsUpdateDialogButtonMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                    .addComponent(label1)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(addArtistButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(141, 141, 141)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(removeArtistButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(artistUpdateDialogButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(141, 141, 141)))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                    .addComponent(label2)
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(addAlbumOpenDialog, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(109, 109, 109)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(albumsUpdateDialogButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeAlbumButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(109, 109, 109)))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label3)
                        .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(songsUpdateDialogButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addSongButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeSongButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(253, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(label3)
                        .addComponent(label2))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                        .addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                        .addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addArtistButton)
                        .addComponent(addAlbumOpenDialog)
                        .addComponent(addSongButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(removeArtistButton)
                        .addComponent(removeAlbumButton)
                        .addComponent(removeSongButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(artistUpdateDialogButton)
                        .addComponent(albumsUpdateDialogButton)
                        .addComponent(songsUpdateDialogButton))
                    .addContainerGap(215, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== addArtistDialog ========
        {
            Container addArtistDialogContentPane = addArtistDialog.getContentPane();

            //---- label4 ----
            label4.setText("Artist name");

            //---- addArtistButton2 ----
            addArtistButton2.setText("ADD");
            addArtistButton2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addArtistButton2MouseClicked(e);
                }
            });

            //---- addArtistCloseButton ----
            addArtistCloseButton.setText("CLOSE");
            addArtistCloseButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addArtistCloseButtonMouseClicked(e);
                }
            });

            GroupLayout addArtistDialogContentPaneLayout = new GroupLayout(addArtistDialogContentPane);
            addArtistDialogContentPane.setLayout(addArtistDialogContentPaneLayout);
            addArtistDialogContentPaneLayout.setHorizontalGroup(
                addArtistDialogContentPaneLayout.createParallelGroup()
                    .addGroup(addArtistDialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap(41, Short.MAX_VALUE)
                        .addGroup(addArtistDialogContentPaneLayout.createParallelGroup()
                            .addGroup(addArtistDialogContentPaneLayout.createSequentialGroup()
                                .addComponent(label4)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(addArtistDialogContentPaneLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(addArtistDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(addArtistTextField, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addArtistDialogContentPaneLayout.createSequentialGroup()
                                        .addComponent(addArtistButton2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(addArtistCloseButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50))))
            );
            addArtistDialogContentPaneLayout.setVerticalGroup(
                addArtistDialogContentPaneLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, addArtistDialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap(65, Short.MAX_VALUE)
                        .addComponent(label4)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addArtistTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addArtistDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(addArtistCloseButton)
                            .addComponent(addArtistButton2))
                        .addGap(60, 60, 60))
            );
            addArtistDialog.pack();
            addArtistDialog.setLocationRelativeTo(addArtistDialog.getOwner());
        }

        //======== addAlbumDialog ========
        {
            Container addAlbumDialogContentPane = addAlbumDialog.getContentPane();

            //---- label5 ----
            label5.setText("Album name");

            //---- addAlbumAddButton ----
            addAlbumAddButton.setText("ADD");
            addAlbumAddButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addAlbumAddButtonMouseClicked(e);
                }
            });

            //---- addAlbumCloseButton ----
            addAlbumCloseButton.setText("CLOSE");
            addAlbumCloseButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button2MouseClicked(e);
                    addAlbumCloseButtonMouseClicked(e);
                }
            });

            GroupLayout addAlbumDialogContentPaneLayout = new GroupLayout(addAlbumDialogContentPane);
            addAlbumDialogContentPane.setLayout(addAlbumDialogContentPaneLayout);
            addAlbumDialogContentPaneLayout.setHorizontalGroup(
                addAlbumDialogContentPaneLayout.createParallelGroup()
                    .addGroup(addAlbumDialogContentPaneLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(addAlbumDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(addAlbumDialogContentPaneLayout.createSequentialGroup()
                                .addComponent(addAlbumAddButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addAlbumCloseButton, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(addAlbumAlbumNameTextField, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5))
                        .addContainerGap(77, Short.MAX_VALUE))
            );
            addAlbumDialogContentPaneLayout.setVerticalGroup(
                addAlbumDialogContentPaneLayout.createParallelGroup()
                    .addGroup(addAlbumDialogContentPaneLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(label5)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addAlbumAlbumNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addAlbumDialogContentPaneLayout.createParallelGroup()
                            .addComponent(addAlbumCloseButton)
                            .addComponent(addAlbumAddButton))
                        .addContainerGap(69, Short.MAX_VALUE))
            );
            addAlbumDialog.pack();
            addAlbumDialog.setLocationRelativeTo(addAlbumDialog.getOwner());
        }

        //======== addSongDialog ========
        {
            Container addSongDialogContentPane = addSongDialog.getContentPane();

            //---- label6 ----
            label6.setText("Song title");

            //---- addSongAddButton ----
            addSongAddButton.setText("ADD");
            addSongAddButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addSongAddButtonMouseClicked(e);
                }
            });

            //---- addSongCloseButton ----
            addSongCloseButton.setText("CLOSE");

            GroupLayout addSongDialogContentPaneLayout = new GroupLayout(addSongDialogContentPane);
            addSongDialogContentPane.setLayout(addSongDialogContentPaneLayout);
            addSongDialogContentPaneLayout.setHorizontalGroup(
                addSongDialogContentPaneLayout.createParallelGroup()
                    .addGroup(addSongDialogContentPaneLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(addSongDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(addSongDialogContentPaneLayout.createSequentialGroup()
                                .addComponent(addSongAddButton)
                                .addGap(18, 18, 18)
                                .addComponent(addSongCloseButton))
                            .addComponent(label6)
                            .addComponent(addSongTextField))
                        .addContainerGap(46, Short.MAX_VALUE))
            );
            addSongDialogContentPaneLayout.setVerticalGroup(
                addSongDialogContentPaneLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, addSongDialogContentPaneLayout.createSequentialGroup()
                        .addContainerGap(63, Short.MAX_VALUE)
                        .addComponent(label6)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addSongTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addSongDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(addSongAddButton)
                            .addComponent(addSongCloseButton))
                        .addGap(61, 61, 61))
            );
            addSongDialog.pack();
            addSongDialog.setLocationRelativeTo(addSongDialog.getOwner());
        }

        //======== updateArtistDialog ========
        {
            Container updateArtistDialogContentPane = updateArtistDialog.getContentPane();

            //---- label7 ----
            label7.setText("Artist name");

            //---- updateArtist ----
            updateArtist.setText("UPDATE");
            updateArtist.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeArtistMouseClicked(e);
                    updateArtistMouseClicked(e);
                }
            });

            //---- button8 ----
            button8.setText("CLOSE");
            button8.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button8MouseClicked(e);
                }
            });

            GroupLayout updateArtistDialogContentPaneLayout = new GroupLayout(updateArtistDialogContentPane);
            updateArtistDialogContentPane.setLayout(updateArtistDialogContentPaneLayout);
            updateArtistDialogContentPaneLayout.setHorizontalGroup(
                updateArtistDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateArtistDialogContentPaneLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(updateArtistDialogContentPaneLayout.createParallelGroup()
                            .addComponent(label7)
                            .addGroup(updateArtistDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(updateArtistDialogContentPaneLayout.createSequentialGroup()
                                    .addComponent(updateArtist)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button8))
                                .addComponent(updateArtistTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                        .addContainerGap(56, Short.MAX_VALUE))
            );
            updateArtistDialogContentPaneLayout.setVerticalGroup(
                updateArtistDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateArtistDialogContentPaneLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(label7)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateArtistTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(updateArtistDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(updateArtist)
                            .addComponent(button8))
                        .addContainerGap(51, Short.MAX_VALUE))
            );
            updateArtistDialog.pack();
            updateArtistDialog.setLocationRelativeTo(updateArtistDialog.getOwner());
        }

        //======== updateAlbumDialog ========
        {
            Container updateAlbumDialogContentPane = updateAlbumDialog.getContentPane();

            //---- label8 ----
            label8.setText("Album name");

            //---- updateAlbum ----
            updateAlbum.setText("UPDATE");
            updateAlbum.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updateAlbumMouseClicked(e);
                }
            });

            //---- button10 ----
            button10.setText("CLOSE");
            button10.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button10MouseClicked(e);
                }
            });

            GroupLayout updateAlbumDialogContentPaneLayout = new GroupLayout(updateAlbumDialogContentPane);
            updateAlbumDialogContentPane.setLayout(updateAlbumDialogContentPaneLayout);
            updateAlbumDialogContentPaneLayout.setHorizontalGroup(
                updateAlbumDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateAlbumDialogContentPaneLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(updateAlbumDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(label8)
                            .addGroup(updateAlbumDialogContentPaneLayout.createSequentialGroup()
                                .addComponent(updateAlbum)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button10))
                            .addComponent(updateAlbumTextfield, GroupLayout.Alignment.TRAILING))
                        .addContainerGap(69, Short.MAX_VALUE))
            );
            updateAlbumDialogContentPaneLayout.setVerticalGroup(
                updateAlbumDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateAlbumDialogContentPaneLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(label8)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateAlbumTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(updateAlbumDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(updateAlbum)
                            .addComponent(button10))
                        .addContainerGap(40, Short.MAX_VALUE))
            );
            updateAlbumDialog.pack();
            updateAlbumDialog.setLocationRelativeTo(updateAlbumDialog.getOwner());
        }

        //======== updateSongDialog ========
        {
            Container updateSongDialogContentPane = updateSongDialog.getContentPane();

            //---- label9 ----
            label9.setText("Song title");

            //---- updateSong ----
            updateSong.setText("UPDATE");
            updateSong.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updateSongMouseClicked(e);
                }
            });

            //---- button12 ----
            button12.setText("CLOSE");
            button12.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button12MouseClicked(e);
                }
            });

            GroupLayout updateSongDialogContentPaneLayout = new GroupLayout(updateSongDialogContentPane);
            updateSongDialogContentPane.setLayout(updateSongDialogContentPaneLayout);
            updateSongDialogContentPaneLayout.setHorizontalGroup(
                updateSongDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateSongDialogContentPaneLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(updateSongDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(updateSongDialogContentPaneLayout.createSequentialGroup()
                                .addComponent(updateSong)
                                .addGap(18, 18, 18)
                                .addComponent(button12))
                            .addComponent(label9)
                            .addComponent(updateSongTextfield))
                        .addContainerGap(58, Short.MAX_VALUE))
            );
            updateSongDialogContentPaneLayout.setVerticalGroup(
                updateSongDialogContentPaneLayout.createParallelGroup()
                    .addGroup(updateSongDialogContentPaneLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(label9)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateSongTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(updateSongDialogContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(updateSong)
                            .addComponent(button12))
                        .addContainerGap(30, Short.MAX_VALUE))
            );
            updateSongDialog.pack();
            updateSongDialog.setLocationRelativeTo(updateSongDialog.getOwner());
        }
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vilmantas
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList artistsList;
    private JScrollPane scrollPane2;
    private JList albumsList;
    private JScrollPane scrollPane3;
    private JList songList;
    private JLabel label2;
    private JLabel label3;
    private JButton addArtistButton;
    private JButton addAlbumOpenDialog;
    private JButton addSongButton;
    private JButton removeArtistButton;
    private JButton artistUpdateDialogButton;
    private JButton removeAlbumButton;
    private JButton albumsUpdateDialogButton;
    private JButton removeSongButton;
    private JButton songsUpdateDialogButton;
    private JDialog addArtistDialog;
    private JTextField addArtistTextField;
    private JLabel label4;
    private JButton addArtistButton2;
    private JButton addArtistCloseButton;
    private JDialog addAlbumDialog;
    private JLabel label5;
    private JTextField addAlbumAlbumNameTextField;
    private JButton addAlbumAddButton;
    private JButton addAlbumCloseButton;
    private JDialog addSongDialog;
    private JTextField addSongTextField;
    private JLabel label6;
    private JButton addSongAddButton;
    private JButton addSongCloseButton;
    private JDialog updateArtistDialog;
    private JLabel label7;
    private JTextField updateArtistTextfield;
    private JButton updateArtist;
    private JButton button8;
    private JDialog updateAlbumDialog;
    private JLabel label8;
    private JTextField updateAlbumTextfield;
    private JButton updateAlbum;
    private JButton button10;
    private JDialog updateSongDialog;
    private JLabel label9;
    private JTextField updateSongTextfield;
    private JButton updateSong;
    private JButton button12;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

}
