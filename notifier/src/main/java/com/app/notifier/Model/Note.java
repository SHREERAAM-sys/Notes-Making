package com.app.notifier.Model;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; 
	@Column(name = "noteName")
	  private String noteName;
	@Column(name = "noteDescription")
	  private String noteDescription;
	@Column(name="startDate")
	private Date startDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="remainderDate")
	private Date remainderDate;
	
	@OneToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_status"))
	private Status status;
	
	@OneToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_tag"))
	private Tag tag;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_noteBook"))
	private NoteBook noteBook;
	
	public Note(){
		
	}
	
	public Note(Integer id, String noteName, String noteDescription, Date startDate, Date endDate, Date remainderDate,
			Status status, Tag tag, NoteBook noteBook) {
		this.id = id;
		this.noteName = noteName;
		this.noteDescription = noteDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remainderDate = remainderDate;
		this.status = status;
		this.tag = tag;
		this.noteBook = noteBook;
	}
	public Note (String noteName, String noteDescription,Date startDate2,Date endDate2, Date remainderDate2,
			Status status, Tag tag, NoteBook noteBook) {
		this.noteName = noteName;
		this.noteDescription =noteDescription;
		this.startDate =startDate2;
		this.endDate = endDate2;
		this.remainderDate = remainderDate2;
		this.status = status;
		this.tag = tag;
		this.noteBook = noteBook;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public String getNoteDescription() {
		return noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRemainderDate() {
		return remainderDate;
	}

	public void setRemainderDate(Date remainderDate) {
		this.remainderDate = remainderDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public NoteBook getNoteBook() {
		return noteBook;
	}

	public void setNoteBook(NoteBook noteBook) {
		this.noteBook = noteBook;
	}	
	
}
