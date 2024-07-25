package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class EmojiExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmojiExample() {
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

        public Criteria andEmoDescIsNull() {
            addCriterion("EMO_DESC is null");
            return (Criteria) this;
        }

        public Criteria andEmoDescIsNotNull() {
            addCriterion("EMO_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andEmoDescEqualTo(String value) {
            addCriterion("EMO_DESC =", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescNotEqualTo(String value) {
            addCriterion("EMO_DESC <>", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescGreaterThan(String value) {
            addCriterion("EMO_DESC >", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescGreaterThanOrEqualTo(String value) {
            addCriterion("EMO_DESC >=", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescLessThan(String value) {
            addCriterion("EMO_DESC <", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescLessThanOrEqualTo(String value) {
            addCriterion("EMO_DESC <=", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescLike(String value) {
            addCriterion("EMO_DESC like", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescNotLike(String value) {
            addCriterion("EMO_DESC not like", value, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescIn(List<String> values) {
            addCriterion("EMO_DESC in", values, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescNotIn(List<String> values) {
            addCriterion("EMO_DESC not in", values, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescBetween(String value1, String value2) {
            addCriterion("EMO_DESC between", value1, value2, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmoDescNotBetween(String value1, String value2) {
            addCriterion("EMO_DESC not between", value1, value2, "emoDesc");
            return (Criteria) this;
        }

        public Criteria andEmojiIsNull() {
            addCriterion("EMOJI is null");
            return (Criteria) this;
        }

        public Criteria andEmojiIsNotNull() {
            addCriterion("EMOJI is not null");
            return (Criteria) this;
        }

        public Criteria andEmojiEqualTo(String value) {
            addCriterion("EMOJI =", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiNotEqualTo(String value) {
            addCriterion("EMOJI <>", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiGreaterThan(String value) {
            addCriterion("EMOJI >", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiGreaterThanOrEqualTo(String value) {
            addCriterion("EMOJI >=", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiLessThan(String value) {
            addCriterion("EMOJI <", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiLessThanOrEqualTo(String value) {
            addCriterion("EMOJI <=", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiLike(String value) {
            addCriterion("EMOJI like", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiNotLike(String value) {
            addCriterion("EMOJI not like", value, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiIn(List<String> values) {
            addCriterion("EMOJI in", values, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiNotIn(List<String> values) {
            addCriterion("EMOJI not in", values, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiBetween(String value1, String value2) {
            addCriterion("EMOJI between", value1, value2, "emoji");
            return (Criteria) this;
        }

        public Criteria andEmojiNotBetween(String value1, String value2) {
            addCriterion("EMOJI not between", value1, value2, "emoji");
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