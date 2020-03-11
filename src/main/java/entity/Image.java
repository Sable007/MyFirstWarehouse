package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Image {
	//通过素材管理中的接口上传多媒体文件，得到的id
		@XStreamAlias("MediaId")
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		public Image(String mediaId) {
			super();
			this.mediaId = mediaId;
		}
		
}
