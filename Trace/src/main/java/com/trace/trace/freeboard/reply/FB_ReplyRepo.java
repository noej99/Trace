package com.trace.trace.freeboard.reply;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FB_ReplyRepo extends CrudRepository<FB_Reply, String>{
	
	//파라미터에 나타나는 글번호가  매개변수 no로 들어간다
	public abstract List<FB_Reply> findByNoOrderByDate(Integer no);
	
	
}
