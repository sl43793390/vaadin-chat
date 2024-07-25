package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GroupInfoExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(String value) {
            addCriterion("GROUP_ID =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(String value) {
            addCriterion("GROUP_ID <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(String value) {
            addCriterion("GROUP_ID >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_ID >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(String value) {
            addCriterion("GROUP_ID <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(String value) {
            addCriterion("GROUP_ID <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLike(String value) {
            addCriterion("GROUP_ID like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotLike(String value) {
            addCriterion("GROUP_ID not like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<String> values) {
            addCriterion("GROUP_ID in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<String> values) {
            addCriterion("GROUP_ID not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(String value1, String value2) {
            addCriterion("GROUP_ID between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(String value1, String value2) {
            addCriterion("GROUP_ID not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("GROUP_NAME is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("GROUP_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("GROUP_NAME =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("GROUP_NAME <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("GROUP_NAME >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("GROUP_NAME <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("GROUP_NAME like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("GROUP_NAME not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("GROUP_NAME in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("GROUP_NAME not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("GROUP_NAME between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("GROUP_NAME not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerIsNull() {
            addCriterion("GROUP_OWNER is null");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerIsNotNull() {
            addCriterion("GROUP_OWNER is not null");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerEqualTo(String value) {
            addCriterion("GROUP_OWNER =", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerNotEqualTo(String value) {
            addCriterion("GROUP_OWNER <>", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerGreaterThan(String value) {
            addCriterion("GROUP_OWNER >", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_OWNER >=", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerLessThan(String value) {
            addCriterion("GROUP_OWNER <", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerLessThanOrEqualTo(String value) {
            addCriterion("GROUP_OWNER <=", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerLike(String value) {
            addCriterion("GROUP_OWNER like", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerNotLike(String value) {
            addCriterion("GROUP_OWNER not like", value, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerIn(List<String> values) {
            addCriterion("GROUP_OWNER in", values, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerNotIn(List<String> values) {
            addCriterion("GROUP_OWNER not in", values, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerBetween(String value1, String value2) {
            addCriterion("GROUP_OWNER between", value1, value2, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupOwnerNotBetween(String value1, String value2) {
            addCriterion("GROUP_OWNER not between", value1, value2, "groupOwner");
            return (Criteria) this;
        }

        public Criteria andGroupImgIsNull() {
            addCriterion("GROUP_IMG is null");
            return (Criteria) this;
        }

        public Criteria andGroupImgIsNotNull() {
            addCriterion("GROUP_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andGroupImgEqualTo(String value) {
            addCriterion("GROUP_IMG =", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgNotEqualTo(String value) {
            addCriterion("GROUP_IMG <>", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgGreaterThan(String value) {
            addCriterion("GROUP_IMG >", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_IMG >=", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgLessThan(String value) {
            addCriterion("GROUP_IMG <", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgLessThanOrEqualTo(String value) {
            addCriterion("GROUP_IMG <=", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgLike(String value) {
            addCriterion("GROUP_IMG like", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgNotLike(String value) {
            addCriterion("GROUP_IMG not like", value, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgIn(List<String> values) {
            addCriterion("GROUP_IMG in", values, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgNotIn(List<String> values) {
            addCriterion("GROUP_IMG not in", values, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgBetween(String value1, String value2) {
            addCriterion("GROUP_IMG between", value1, value2, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupImgNotBetween(String value1, String value2) {
            addCriterion("GROUP_IMG not between", value1, value2, "groupImg");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementIsNull() {
            addCriterion("GROUP_ANNOUNCEMENT is null");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementIsNotNull() {
            addCriterion("GROUP_ANNOUNCEMENT is not null");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementEqualTo(String value) {
            addCriterion("GROUP_ANNOUNCEMENT =", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementNotEqualTo(String value) {
            addCriterion("GROUP_ANNOUNCEMENT <>", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementGreaterThan(String value) {
            addCriterion("GROUP_ANNOUNCEMENT >", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_ANNOUNCEMENT >=", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementLessThan(String value) {
            addCriterion("GROUP_ANNOUNCEMENT <", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementLessThanOrEqualTo(String value) {
            addCriterion("GROUP_ANNOUNCEMENT <=", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementLike(String value) {
            addCriterion("GROUP_ANNOUNCEMENT like", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementNotLike(String value) {
            addCriterion("GROUP_ANNOUNCEMENT not like", value, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementIn(List<String> values) {
            addCriterion("GROUP_ANNOUNCEMENT in", values, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementNotIn(List<String> values) {
            addCriterion("GROUP_ANNOUNCEMENT not in", values, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementBetween(String value1, String value2) {
            addCriterion("GROUP_ANNOUNCEMENT between", value1, value2, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupAnnouncementNotBetween(String value1, String value2) {
            addCriterion("GROUP_ANNOUNCEMENT not between", value1, value2, "groupAnnouncement");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNull() {
            addCriterion("GROUP_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNotNull() {
            addCriterion("GROUP_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusEqualTo(String value) {
            addCriterion("GROUP_STATUS =", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotEqualTo(String value) {
            addCriterion("GROUP_STATUS <>", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThan(String value) {
            addCriterion("GROUP_STATUS >", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_STATUS >=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThan(String value) {
            addCriterion("GROUP_STATUS <", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThanOrEqualTo(String value) {
            addCriterion("GROUP_STATUS <=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLike(String value) {
            addCriterion("GROUP_STATUS like", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotLike(String value) {
            addCriterion("GROUP_STATUS not like", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIn(List<String> values) {
            addCriterion("GROUP_STATUS in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotIn(List<String> values) {
            addCriterion("GROUP_STATUS not in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusBetween(String value1, String value2) {
            addCriterion("GROUP_STATUS between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotBetween(String value1, String value2) {
            addCriterion("GROUP_STATUS not between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andDtCreateIsNull() {
            addCriterion("DT_CREATE is null");
            return (Criteria) this;
        }

        public Criteria andDtCreateIsNotNull() {
            addCriterion("DT_CREATE is not null");
            return (Criteria) this;
        }

        public Criteria andDtCreateEqualTo(Date value) {
            addCriterion("DT_CREATE =", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateNotEqualTo(Date value) {
            addCriterion("DT_CREATE <>", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateGreaterThan(Date value) {
            addCriterion("DT_CREATE >", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("DT_CREATE >=", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateLessThan(Date value) {
            addCriterion("DT_CREATE <", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateLessThanOrEqualTo(Date value) {
            addCriterion("DT_CREATE <=", value, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateIn(List<Date> values) {
            addCriterion("DT_CREATE in", values, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateNotIn(List<Date> values) {
            addCriterion("DT_CREATE not in", values, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateBetween(Date value1, Date value2) {
            addCriterion("DT_CREATE between", value1, value2, "dtCreate");
            return (Criteria) this;
        }

        public Criteria andDtCreateNotBetween(Date value1, Date value2) {
            addCriterion("DT_CREATE not between", value1, value2, "dtCreate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}