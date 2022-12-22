package com.simplesearch.repository;

import com.simplesearch.entity.session.IndexingSession;
import com.simplesearch.entity.session.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String GET_SESSION_QUERY = "SELECT * FROM session WHERE uuid = ?";
    private static final String SAVE_SESSION_QUERY = "INSERT INTO session (uuid, status, create_time, total, indexed) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SESSION_QUERY = "UPDATE session SET status = ?, indexed = ? WHERE uuid = ?";

    @Autowired
    public SessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public IndexingSession findSessionInfo(String uuid) {
        return jdbcTemplate.queryForObject(GET_SESSION_QUERY, (rs, rowNum) ->
                new IndexingSession(rs.getString("uuid"),
                        SessionStatus.valueOf(rs.getString("status")),
                        new java.util.Date(rs.getTime("create_time").getTime()),
                        rs.getInt("indexed"), rs.getInt("total")), uuid);
    }

    public void saveSession(IndexingSession indexingSession) {
        jdbcTemplate.update(SAVE_SESSION_QUERY, indexingSession.getId(), indexingSession.getStatus().name(), indexingSession.getStartTime(), indexingSession.getTotal(), indexingSession.getIndexed());
    }

    public void updateSession(IndexingSession indexingSession) {
        jdbcTemplate.update(UPDATE_SESSION_QUERY, indexingSession.getStatus(), indexingSession.getIndexed(), indexingSession);
    }

}
