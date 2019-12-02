package com.vinodkmr.tech.model;

import java.util.Date;

public class TechArticle{

	private String header;
	private String imageLink;
	private String articleLink;
	private Date publishedDate;
	
	public TechArticle(String header, String imageLink, String articleLink, Date publishedDate) {
		super();
		this.header = header;
		this.imageLink = imageLink;
		this.articleLink = articleLink;
		this.publishedDate = publishedDate;
	}

	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return "TechArticle [header=" + header + ", imageLink=" + imageLink + ", articleLink=" + articleLink
				+ ", publishedDate=" + publishedDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleLink == null) ? 0 : articleLink.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((imageLink == null) ? 0 : imageLink.hashCode());
		result = prime * result + ((publishedDate == null) ? 0 : publishedDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TechArticle other = (TechArticle) obj;
		if (articleLink == null) {
			if (other.articleLink != null)
				return false;
		} else if (!articleLink.equals(other.articleLink))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (imageLink == null) {
			if (other.imageLink != null)
				return false;
		} else if (!imageLink.equals(other.imageLink))
			return false;
		if (publishedDate == null) {
			if (other.publishedDate != null)
				return false;
		} else if (!publishedDate.equals(other.publishedDate))
			return false;
		return true;
	}


}
