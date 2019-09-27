package kr.co.itcen.mysite.search;

import kr.co.itcen.mysite.repository.Paging;

public class Search {

		private int page;
		private String kwd;
		private Paging pagination;
		private int stNo;
		private int endNo;
		
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public String getKwd() {
			return kwd;
		}
		public void setKwd(String kwd) {
			this.kwd = kwd;
		}
		public Paging getPagination() {
			return pagination;
		}
		public void setPagination(Paging pagination) {
			this.pagination = pagination;
		}
		public int getStNo() {
			return stNo;
		}
		public void setStNo(int stNo) {
			this.stNo = stNo;
		}
		public int getEndNo() {
			return endNo;
		}
		public void setEndNo(int endNo) {
			this.endNo = endNo;
		}
		@Override
		public String toString() {
			return "Search [page=" + page + ", kwd=" + kwd + ", pagination=" + pagination + ", stNo=" + stNo
					+ ", endNo=" + endNo + "]";
		}
	
		
		
	}


