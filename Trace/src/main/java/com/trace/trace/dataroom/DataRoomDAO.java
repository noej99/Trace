package com.trace.trace.dataroom;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trace.trace.member.MemberWriter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DataRoomDAO {

	@Autowired
	DataRoomRepo drr;

	@Autowired
	DataRoomCountRepo drcr;

	@Autowired
	DataRoomFileRepo drfr;

	@Autowired
	DataRoomImageRepo drir;

	@Autowired
	DataRoomReplyRepo drrr;

	@Autowired
	DataRoomReReplyRepo drrrr;

	@Autowired
	DataRoomLikeCheckRepo drlcr;

	@Value("${file.dir}")
	private String fileDir;

	@Value("${temp.dir}")
	private String tempDir;

	private DataRoomCount drc;

	private DataRoomLikeCheck drlc;

	private int postPerPage;

	private int pagePerRow;

	public DataRoomDAO() {
		drc = new DataRoomCount();
		postPerPage = 10;
		pagePerRow = 10;
	}

	public void test(HttpServletRequest req, int i) {
		DataRoom dr = new DataRoom();
		try {
			dr.setTitle("test" + i);
			dr.setContent("<p>테스트입니다" + i + "</p><p>테스트입니다" + i + "</p>");
			MemberWriter test = new MemberWriter();
			test.setId1("test@test.com");
			dr.setDrMw(test);
			dr.setDate(new Date());
			Integer no = drr.save(dr).getNo();
			drc.setNo(no);
			drc.setLike(0);
			drc.setView(0);
			drcr.save(drc);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "글쓰기실패");
		}
	}

	public void reply(DataRoomReply dr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "댓글쓰기실패");
				return;
			}
			dr.setDate(new Date());
			drrr.save(dr);
			req.setAttribute("result", "댓글쓰기성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글쓰기실패");
		}
	}

	public void reReply(DataRoomReReply dr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "대댓글쓰기실패");
				return;
			}
			dr.setDate(new Date());
			drrrr.save(dr);
			req.setAttribute("result", "대댓글쓰기성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "대댓글쓰기실패");
		}
	}

	public void deleteReply(int no, int rNo) {
		try {
			drrr.deleteById((Integer) rNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteReReply(int no, int rNo) {
		try {
			drrrr.deleteById((Integer) rNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void get(HttpServletRequest req) {
		try {
			req.getSession().setAttribute("temp", null);
			int page = 0;
			int reply = 0;
			if (req.getParameter("page") != null) {
				page = Integer.parseInt(req.getParameter("page")) - 1;
			}
			Sort sort = Sort.by("no").descending();
			Pageable pageable = PageRequest.of(page, postPerPage, sort);
			Page<DataRoom> drList = drr.findAll(pageable);
			List<DataRoomCount> drcList = new ArrayList<>();
			List<Integer> replyCount = new ArrayList<>();
			for (DataRoom dr : drList) {
				System.out.println(dr.getNo());
				System.out.println(dr.getTitle());
				reply = 0;
				if (dr.getReplys() != null) {
					reply += dr.getReplys().size();
				}
				if (dr.getReReplys() != null) {
					reply += dr.getReReplys().size();
				}
				replyCount.add(reply);
				drcList.add(drcr.findByNo(dr.getNo()));
			}
			int curPage = drList.getNumber();
			int maxPage = drList.getTotalPages();
			int startPage = curPage / pagePerRow * pagePerRow + 1;
			int endPage = (startPage + pagePerRow - 1 > maxPage ? maxPage : startPage + pagePerRow - 1);
			List<Integer> pageRow = new ArrayList<>();
			for (int i = startPage; i <= endPage; i++) {
				pageRow.add(i);
			}
			req.setAttribute("list", drList);
			req.setAttribute("count", drcList);
			req.setAttribute("isFirst", drList.isFirst());
			req.setAttribute("isLast", drList.isLast());
			req.setAttribute("curPage", curPage + 1);
			req.setAttribute("pageRow", pageRow);
			req.setAttribute("endPage", endPage);
			req.setAttribute("replyCount", replyCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String Like(DataRoom dr) {
		try {
			Integer no = dr.getNo();
			String id = dr.getDrMw().getId1();
			drlc = new DataRoomLikeCheck(no, id);
			if (drlcr.existsByDrNoAndLcId(no, id)) {
				drlcr.deleteById(no);
				drc = drcr.findByNo(no);
				drc.setLike(drc.getLike() - 1);
				drcr.save(drc);
				return "{ \"result\" : \"좋아요를 취소했습니다\" }";
			} else {
				drlcr.save(drlc);
				drc = drcr.findByNo(no);
				drc.setLike(drc.getLike() + 1);
				drcr.save(drc);
				return "{ \"result\" : \"이 게시글을 좋아요 했습니다\" }";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"result\" : \"오류가 발생했습니다\" }";
		}
	}

	public void view(int no, HttpServletRequest req) {
		try {
			req.getSession().setAttribute("temp", null);
//			Integer no = Integer.parseInt(req.getParameter("no"));
			drc = drcr.findByNo(no);
			// 그대로 둘 정보는 그대로 두고, 바꿀거는 바꾸고
			drc.setView(drc.getView() + 1);
			drcr.save(drc);

			DataRoom dr = drr.findByNo(no);
//			List<DataRoomReply> drRe = new ArrayList<>();
//			for (DataRoomReply drdrr : dr.getReplys()) {
//				drRe.add(drdrr);
//				for(DataRoomReReply x : drdrr.getReReplys())
//				drRe.add(x);
//			}

			req.setAttribute("list", dr);
			req.setAttribute("count", drc);
			req.setAttribute("drFiles", drfr.findByDrNo(no));
			req.setAttribute("result", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "조회실패");
		}
	}

	public String getFileName(MultipartFile mf) {
		try {
			String fileName = mf.getOriginalFilename(); // ㅋ ㅋ.png
			String type = fileName.substring(fileName.lastIndexOf(".")); // .png
			fileName = fileName.replace(type, ""); // ㅋ ㅋ

			// UUID : 네트워크 같은데서 중복 안되는 id값 필요할 때 쓰는
			String uuid = UUID.randomUUID().toString();
			return fileName + "_" + uuid + type;

			// 파일명 중복되면 그냥 덮어쓰기
			// 안덮어쓰려면 파일명을 다르게 해야
			// 그 대책은 없음
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String uploadImage(MultipartFile mf, HttpServletRequest req) {
		String fileName = null;
		File f = null;
		String url = null;
		List<String> fileNames = new ArrayList<>();
		if (req.getSession().getAttribute("tempImage") != null) {
			fileNames = (ArrayList<String>) req.getSession().getAttribute("tempImage");
		}
		try {
			fileName = getFileName(mf);
			f = new File(tempDir + "/" + fileName);
			mf.transferTo(f);
//			url = "{\"url\":\"" +tempDir + "/" + fileName + "\"}";
			fileNames.add(fileName);
			fileName = URLEncoder.encode(fileName, "utf-8").replace("+", " ");
			url = "{\"url\":\"<img src='/dataroom.image/" + fileName + "'/>\"}";
			req.getSession().setAttribute("tempImage", fileNames);
//			System.out.println(fileNames);
		} catch (Exception e) {
			e.printStackTrace();
			for (String fn : fileNames) {
				new File(tempDir + "/" + fn).delete();
			}
			return null;
		}
		try {
			return url;
		} catch (Exception e) {
			e.printStackTrace();
			for (String fn : fileNames) {
				new File(tempDir + "/" + fn).delete();
			}
			return null;
		}
	}

	public Resource getImage(String name, HttpServletRequest req) {
		try {
			if (req.getSession().getAttribute("temp") != null && (boolean) req.getSession().getAttribute("temp")) {
				System.out.println("임시");
				return new UrlResource("file:" + tempDir + "/" + URLDecoder.decode(name, "utf-8"));
			}
			System.out.println("업로드폴더");
			return new UrlResource("file:" + fileDir + "/" + URLDecoder.decode(name, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<Resource> getFile(String name) {
		try {
			UrlResource ur = new UrlResource("file:" + fileDir + "/" + name);
			String header = "attachment; filename=\"" + URLEncoder.encode(name, "utf-8").replace("+", " ") + "\"";
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, header).body(ur);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void upload(MultipartFile[] mf, DataRoom dr, HttpServletRequest req) {
		List<String> fileNames = new ArrayList<>();
		String fileName = null;
		File f = null;
		File tempImage = null;
		File realImage = null;
		List<String> tempImages = new ArrayList<>();
		DataRoomFile drf = null;
		DataRoomImage dri = null;
		try {
			if (mf != null) {
				for (MultipartFile mff : mf) {
					if (mff.getOriginalFilename().equals("")) {
						continue;
					}
					fileName = getFileName(mff);
					f = new File(fileDir + "/" + fileName);
					mff.transferTo(f);
					fileNames.add(fileName);
				}
				req.setAttribute("result", "파일 업로드 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String fn : fileNames) {
				new File(fileDir + "/" + fn).delete();
			}
			req.setAttribute("result", "파일 업로드 실패");
			return;
		}
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "글쓰기실패(토큰)");
				return;
			}
			dr.setDate(new Date());
			Integer no = drr.save(dr).getNo();
			drc.setNo(no);
			drc.setLike(0);
			drc.setView(0);
			drcr.save(drc);
			for (String fn : fileNames) {
				drf = new DataRoomFile();
				drf.setDrNo(no);
				drf.setFile(fn);
				drfr.save(drf);
			}
			if (req.getSession().getAttribute("tempImage") != null) {
				tempImages = (ArrayList<String>) req.getSession().getAttribute("tempImage");
				for (String ti : tempImages) {
					dri = new DataRoomImage();
					tempImage = new File(tempDir + "/" + ti);
					realImage = new File(fileDir + "/" + ti);
					tempImage.renameTo(realImage);
					dri.setDrNo(no);
					dri.setFile(ti);
					drir.save(dri);
				}
			}
			req.setAttribute("result", "글쓰기성공");
			req.getSession().setAttribute("tempImage", null);
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			for (String fn : fileNames) {
				new File(fileDir + "/" + fn).delete();
			}
			for (String ti : tempImages) {
				new File(tempDir + "/" + ti).delete();
				new File(fileDir + "/" + ti).delete();
			}
			req.setAttribute("result", "글쓰기실패");
		}
	}

	public void delete(int no, HttpServletRequest req) {
		try {
			List<DataRoomFile> fileList = drfr.findByDrNo(no);
			List<DataRoomImage> imageList = drir.findByDrNo(no);
			drr.deleteById(no);
			for (DataRoomFile fn : fileList) {
				new File(fileDir + "/" + fn.getFile()).delete();
			}
			for (DataRoomImage in : imageList) {
				new File(fileDir + "/" + in.getFile()).delete();
			}
			req.setAttribute("result", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제실패");
		}
	}

	public void modify(HttpServletRequest req) {
		req.setAttribute("mod", drr.findByNo(Integer.parseInt(req.getParameter("no"))));
	}

	public void update(DataRoom dr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "수정실패");
				return;
			}
			DataRoom dr2 = drr.findByNo(Integer.parseInt(req.getParameter("modNo")));
			dr.setNo(dr2.getNo());
			dr.setDrMw(dr2.getDrMw());
			dr.setDate(dr2.getDate());
			dr.setContent(dr.getContent().replace("<br>", ""));
			dr.setReplys(dr2.getReplys());
			dr.setReReplys(dr2.getReReplys());
			dr = drr.save(dr);
			req.setAttribute("result", "수정성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "수정실패");
		}
	}

	public void search(HttpServletRequest req) {
		try {
			int page = 0;
			String cate = req.getParameter("category");
			String keyword = req.getParameter("keyword");
			if (req.getParameter("page") != null) {
				page = Integer.parseInt(req.getParameter("page")) - 1;
			}
			Sort sort = Sort.by("no").descending();
			Pageable pageable = PageRequest.of(page, postPerPage, sort);
			Page<DataRoom> drList = null;
			List<DataRoomCount> drcList = new ArrayList<>();
			List<Integer> replyCount = new ArrayList<>();
			if (cate.equals("title")) {
				drList = drr.findByTitleContaining(keyword, pageable);
			} else if (cate.equals("content")) {
				drList = drr.findByContentContaining(keyword, pageable);
			} else if (cate.equals("writer")) {
				drList = drr.findByDrMwContaining(keyword, pageable);
			}
			for (DataRoom dr : drList) {
				replyCount.add(dr.getReplys().size() + dr.getReReplys().size());
				drcList.add(drcr.findByNo(dr.getNo()));
			}
			if (drList.getTotalElements() == 0) {
				req.setAttribute("list", null);
			} else {
				req.setAttribute("list", drList);
			}
			int curPage = drList.getNumber();
			int maxPage = drList.getTotalPages();
			int startPage = curPage / pagePerRow * pagePerRow + 1;
			int endPage = (startPage + pagePerRow - 1 > maxPage ? maxPage : startPage + pagePerRow - 1);
			List<Integer> pageRow = new ArrayList<>();
			for (int i = startPage; i <= endPage; i++) {
				pageRow.add(i);
			}
			req.setAttribute("count", drcList);
			req.setAttribute("isFirst", drList.isFirst());
			req.setAttribute("isLast", drList.isLast());
			req.setAttribute("curPage", curPage + 1);
			req.setAttribute("pageRow", pageRow);
			req.setAttribute("endPage", endPage);
			req.setAttribute("replyCount", replyCount);
			req.setAttribute("category", cate);
			req.setAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
