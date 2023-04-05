// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId, answer.questionId );
  $(".qna-comment-slipp-articles").prepend(template);
  
  let target = $(".qna-comment-count").children().text()
  let increaCount = parseInt( target, 10 ) + 1;
   $(".qna-comment-count").children().text(increaCount);
}

function onError(xhr, status) {
  alert("error");
}


$(".qna-comment input[name=delete-submit]").click(deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();
  
  var target = $(this);
  var queryString = target.closest("form").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : queryString,
    dataType : 'json',
    error: function() {
		alert("error");
	},
    success : function(json) {
		if(json.result.status) {
			target.closest('article').remove();
			
		let decreasetarget = $(".qna-comment-count").children().text()
		let decreaseCount = parseInt( decreasetarget, 10 ) - 1;
		$(".qna-comment-count").children().text(decreaseCount);
		}
	}
  });
}


String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
