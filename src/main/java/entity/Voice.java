package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Voice {
	//语音消息媒体id，可以调用获取临时素材接口拉取数据。
		@XStreamAlias("MediaId")
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		public Voice(String mediaId) {
			super();
			this.mediaId = mediaId;
		}
		
}
