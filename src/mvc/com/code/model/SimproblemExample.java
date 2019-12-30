package com.code.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimproblemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SimproblemExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andSimproblemIdIsNull() {
            addCriterion("simproblem_id is null");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdIsNotNull() {
            addCriterion("simproblem_id is not null");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdEqualTo(Integer value) {
            addCriterion("simproblem_id =", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdNotEqualTo(Integer value) {
            addCriterion("simproblem_id <>", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdGreaterThan(Integer value) {
            addCriterion("simproblem_id >", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("simproblem_id >=", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdLessThan(Integer value) {
            addCriterion("simproblem_id <", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdLessThanOrEqualTo(Integer value) {
            addCriterion("simproblem_id <=", value, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdIn(List<Integer> values) {
            addCriterion("simproblem_id in", values, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdNotIn(List<Integer> values) {
            addCriterion("simproblem_id not in", values, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdBetween(Integer value1, Integer value2) {
            addCriterion("simproblem_id between", value1, value2, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andSimproblemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("simproblem_id not between", value1, value2, "simproblemId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPaperIdIsNull() {
            addCriterion("paper_id is null");
            return (Criteria) this;
        }

        public Criteria andPaperIdIsNotNull() {
            addCriterion("paper_id is not null");
            return (Criteria) this;
        }

        public Criteria andPaperIdEqualTo(Integer value) {
            addCriterion("paper_id =", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdNotEqualTo(Integer value) {
            addCriterion("paper_id <>", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdGreaterThan(Integer value) {
            addCriterion("paper_id >", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("paper_id >=", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdLessThan(Integer value) {
            addCriterion("paper_id <", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdLessThanOrEqualTo(Integer value) {
            addCriterion("paper_id <=", value, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdIn(List<Integer> values) {
            addCriterion("paper_id in", values, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdNotIn(List<Integer> values) {
            addCriterion("paper_id not in", values, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdBetween(Integer value1, Integer value2) {
            addCriterion("paper_id between", value1, value2, "paperId");
            return (Criteria) this;
        }

        public Criteria andPaperIdNotBetween(Integer value1, Integer value2) {
            addCriterion("paper_id not between", value1, value2, "paperId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andPosIsNull() {
            addCriterion("pos is null");
            return (Criteria) this;
        }

        public Criteria andPosIsNotNull() {
            addCriterion("pos is not null");
            return (Criteria) this;
        }

        public Criteria andPosEqualTo(Integer value) {
            addCriterion("pos =", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotEqualTo(Integer value) {
            addCriterion("pos <>", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosGreaterThan(Integer value) {
            addCriterion("pos >", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("pos >=", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosLessThan(Integer value) {
            addCriterion("pos <", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosLessThanOrEqualTo(Integer value) {
            addCriterion("pos <=", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosIn(List<Integer> values) {
            addCriterion("pos in", values, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotIn(List<Integer> values) {
            addCriterion("pos not in", values, "pos");
            return (Criteria) this;
        }

        public Criteria andPosBetween(Integer value1, Integer value2) {
            addCriterion("pos between", value1, value2, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotBetween(Integer value1, Integer value2) {
            addCriterion("pos not between", value1, value2, "pos");
            return (Criteria) this;
        }

        public Criteria andBlanksIsNull() {
            addCriterion("blanks is null");
            return (Criteria) this;
        }

        public Criteria andBlanksIsNotNull() {
            addCriterion("blanks is not null");
            return (Criteria) this;
        }

        public Criteria andBlanksEqualTo(Integer value) {
            addCriterion("blanks =", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksNotEqualTo(Integer value) {
            addCriterion("blanks <>", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksGreaterThan(Integer value) {
            addCriterion("blanks >", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksGreaterThanOrEqualTo(Integer value) {
            addCriterion("blanks >=", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksLessThan(Integer value) {
            addCriterion("blanks <", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksLessThanOrEqualTo(Integer value) {
            addCriterion("blanks <=", value, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksIn(List<Integer> values) {
            addCriterion("blanks in", values, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksNotIn(List<Integer> values) {
            addCriterion("blanks not in", values, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksBetween(Integer value1, Integer value2) {
            addCriterion("blanks between", value1, value2, "blanks");
            return (Criteria) this;
        }

        public Criteria andBlanksNotBetween(Integer value1, Integer value2) {
            addCriterion("blanks not between", value1, value2, "blanks");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdIsNull() {
            addCriterion("fk_course_id is null");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdIsNotNull() {
            addCriterion("fk_course_id is not null");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdEqualTo(Integer value) {
            addCriterion("fk_course_id =", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdNotEqualTo(Integer value) {
            addCriterion("fk_course_id <>", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdGreaterThan(Integer value) {
            addCriterion("fk_course_id >", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("fk_course_id >=", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdLessThan(Integer value) {
            addCriterion("fk_course_id <", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("fk_course_id <=", value, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdIn(List<Integer> values) {
            addCriterion("fk_course_id in", values, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdNotIn(List<Integer> values) {
            addCriterion("fk_course_id not in", values, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("fk_course_id between", value1, value2, "fkCourseId");
            return (Criteria) this;
        }

        public Criteria andFkCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("fk_course_id not between", value1, value2, "fkCourseId");
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