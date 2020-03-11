package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Video {
	//通过素材管理中的接口上传多媒体文件，得到的id
		@XStreamAlias("Content")
		private String mediaId;
		
		@XStreamAlias("Title")
		private String title;
		
		@XStreamAlias("Description")
		private String description;
		
		public String getMediaId() {
			return mediaId;
		}
		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Video(String mediaId, String title, String description) {
			super();
			this.mediaId = mediaId;
			this.title = title;
			this.description = description;
		}
		
}
