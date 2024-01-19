package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {

	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		List<Song> songList=songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		
		return "createPlaylist";
		
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//Adding user entered data into Playlist table(Updating Playlist Table)
		playlistService.addPlaylist(playlist);
		
		//getting the all Songs From the PlayList Table(Song Column) 
		List<Song> songList=playlist.getSongs();
		
		//for Each loop to save the every selected Song by the user to the Song Table(Song Object)
		//Updating the Song Object(playlist column in Song Table)
		for(Song s: songList) {
			//updating Song Object(playlist column) in db
			s.getPlaylist().add(playlist);	
			
			//Giving the option for User to edit the song if thr link is Wrong
			songService.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		//Fetching the all Playlists
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylists();
		
		model.addAttribute("allPlaylists", allPlaylists);
		
		//oepning the DisplayPlayList page to display Playlists on the web Page(User)
		return "displayPlaylists";
		
	}
}
