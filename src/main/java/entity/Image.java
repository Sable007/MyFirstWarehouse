package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Image {
	//ͨ���زĹ����еĽӿ��ϴ���ý���ļ����õ���id
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
