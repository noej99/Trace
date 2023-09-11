package com.trace.trace.freeboard.reply;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FB_ReReplyRepo extends CrudRepository<FB_ReplyRe, String>{
	
	public abstract List<FB_ReplyRe> findByNoOrderByDate(Integer no);
}
