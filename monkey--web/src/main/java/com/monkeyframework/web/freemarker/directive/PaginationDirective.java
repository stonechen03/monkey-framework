package com.monkeyframework.web.freemarker.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.monkeyframework.web.freemarker.util.DirectiveUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("paginationDirective")
public class PaginationDirective extends BaseDirective {
	
	public static final String TAG_NAME = "pagination";

	private static final String PATTERN_PARAMETER_NAME = "pattern";

	private static final String PAGE_NUMBER_PARAMETER_NAME = "pageNumber";

	private static final String TOTAL_PAGES_PARAMETER_NAME = "totalPages";

	private static final String SEGMENT_COUNT_PARAMETER_NAME = "segmentCount";

	private static final String PATTERN_VARIABLE_NAME = "pattern";

	private static final String PAGE_NUMBER_VARIABLE_NAME = "pageNumber";

	private static final String PAGE_COUNT_VARIABLE_NAME = "totalPages";

	private static final String SEGMENT_COUNT_VARIABLE_NAME = "segmentCount";

	private static final String HAS_PREVIOUS_VARIABLE_NAME = "hasPrevious";

	private static final String HAS_NEXT_VARIABLE_NAME = "hasNext";

	private static final String IS_FIRST_VARIABLE_NAME = "isFirst";

	private static final String IS_LAST_VARIABLE_NAME = "isLast";

	private static final String PREVIOUS_PAGE_NUMBER_VARIABLE_NAME = "previousPageNumber";

	private static final String NEXT_PAGE_NUMBER_VARIABLE_NAME = "nextPageNumber";

	private static final String FIRST_PAGE_NUMBER_VARIABLE_NAME = "firstPageNumber";

	private static final String LAST_PAGE_NUMBER_VARIABLE_NAME = "lastPageNumber";

	private static final String SEGMENT_VARIABLE_NAME = "segment";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String pattern = (String)DirectiveUtil.getObjectParameter(PATTERN_PARAMETER_NAME, params);
		Object pageNumberObject = DirectiveUtil.getObjectParameter(PAGE_NUMBER_PARAMETER_NAME, params);
		Object totalPagesObject = DirectiveUtil.getObjectParameter(TOTAL_PAGES_PARAMETER_NAME, params);
		Object segmentCountObject = DirectiveUtil.getObjectParameter(SEGMENT_COUNT_PARAMETER_NAME, params);
		
		Long pageNumber = Long.parseLong(pageNumberObject.toString());
		Long totalPages = Long.parseLong(totalPagesObject.toString());
		Long segmentCount = 5L;

		if (pageNumber == null || pageNumber < 1) {
			pageNumber = 1L;
		}
		if (totalPages == null || totalPages < 1) {
			totalPages = 1L;
		}
		 
		if (null != segmentCountObject) {
			segmentCount = Long.parseLong(segmentCountObject.toString());
			if(segmentCount < 1) {
				segmentCount = 5L;
			}
		}
		
		boolean hasPrevious = pageNumber > 1;
		boolean hasNext = pageNumber < totalPages;
		boolean isFirst = pageNumber == 1;
		boolean isLast = pageNumber.equals(totalPages);
		long previousPageNumber = pageNumber - 1;
		long nextPageNumber = pageNumber + 1;
		long firstPageNumber = 1;
		long lastPageNumber = totalPages;
		long startSegmentPageNumber = pageNumber - (int) Math.floor((segmentCount - 1) / 2D);
		long endSegmentPageNumber = pageNumber + (int) Math.ceil((segmentCount - 1) / 2D);
		if (startSegmentPageNumber < 1) {
			startSegmentPageNumber = 1;
		}
		if (endSegmentPageNumber > totalPages) {
			endSegmentPageNumber = totalPages;
		}
		List<Long> segment = new ArrayList<Long>();
		for (long i = startSegmentPageNumber; i <= endSegmentPageNumber; i++) {
			segment.add(i);
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PATTERN_VARIABLE_NAME, pattern);
		variables.put(PAGE_NUMBER_VARIABLE_NAME, pageNumber);
		variables.put(PAGE_COUNT_VARIABLE_NAME, totalPages);
		variables.put(SEGMENT_COUNT_VARIABLE_NAME, segmentCount);
		variables.put(HAS_PREVIOUS_VARIABLE_NAME, hasPrevious);
		variables.put(HAS_NEXT_VARIABLE_NAME, hasNext);
		variables.put(IS_FIRST_VARIABLE_NAME, isFirst);
		variables.put(IS_LAST_VARIABLE_NAME, isLast);
		variables.put(PREVIOUS_PAGE_NUMBER_VARIABLE_NAME, previousPageNumber);
		variables.put(NEXT_PAGE_NUMBER_VARIABLE_NAME, nextPageNumber);
		variables.put(FIRST_PAGE_NUMBER_VARIABLE_NAME, firstPageNumber);
		variables.put(LAST_PAGE_NUMBER_VARIABLE_NAME, lastPageNumber);
		variables.put(SEGMENT_VARIABLE_NAME, segment);
		setLocalVariables(variables, env, body);
	}

}