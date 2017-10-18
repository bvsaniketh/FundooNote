package com.bridgeit.Model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleProfile {
	
		
		private List<GmailEmail>emails;
		
		private String displayName;

		public List<GmailEmail> getEmails() {
			return emails;
		}

		public void setEmails(List<GmailEmail> emails) {
			this.emails = emails;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return "GoogleProfile [emails=" + emails + ", displayName=" + displayName + "]";
		}


}
