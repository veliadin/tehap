package com.vadin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long chatId;

	private long senderOd;

	private long recipientId;

	private long senderName;

	private long recipientName;

	private String content;

	private Date timestamp;

	private String status;

	public ChatMessage() {
		super();
	}

	public ChatMessage(long id, long chatId, long senderOd, long recipientId, long senderName, long recipientName,
			String content, Date timestamp, String status) {
		super();
		this.id = id;
		this.chatId = chatId;
		this.senderOd = senderOd;
		this.recipientId = recipientId;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.content = content;
		this.timestamp = timestamp;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public long getSenderOd() {
		return senderOd;
	}

	public void setSenderOd(long senderOd) {
		this.senderOd = senderOd;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}

	public long getSenderName() {
		return senderName;
	}

	public void setSenderName(long senderName) {
		this.senderName = senderName;
	}

	public long getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(long recipientName) {
		this.recipientName = recipientName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
