package com.app.notifier.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="notebook")
public class NoteBook {
	
	 	@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id")
	    private Integer id;
	 	
		@Column(name = "noteBookName")
	    private String noteBookName;
			
		@ManyToOne
		@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER"))
		private User user;
		
		
		public NoteBook()
		{
			
		}
		
		public NoteBook(String noteBookName,User user)
		{
			this.noteBookName=noteBookName;
			this.user=user;
		}
		
		public NoteBook(Integer id, String noteBookName,User user)
		{
			this.id = id;
			this.noteBookName = noteBookName;
			this.user=user;
		}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNoteBookName() {
			return noteBookName;
		}
		public void setNoteBookName(String noteBookName) {
			this.noteBookName = noteBookName;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		 

}
