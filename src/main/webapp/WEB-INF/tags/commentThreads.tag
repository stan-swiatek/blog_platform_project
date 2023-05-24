<%@ attribute name="list" type="java.util.List" required="true" %>
<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="commentThreads" tagdir="/WEB-INF/tags" %>


<c:forEach items="${list}" var="thread">
	<div class="comment-thread__indentation comment-thread__comment-box">
		<c:set var="creator" value="${thread.comment.creator}"/>
		<div class="comment-thread__comment-box__header">
			<a href="/UserProfile/${creator.id}"><p>${creator.username}</p></a>
			<div class="post-time-stacker">
				<p>${thread.comment.readablePostTime}</p>
				<p>${thread.comment.readableEditTime}</p>
			</div>
		</div>
		<div class="comment-thread__comment-box__content" id="comment-text-${thread.comment.id}">
			<p>${thread.comment.content}</p>
		</div>
		<div class="comment-thread__comment-box__reply-button">
			<form action="/comment/${thread.comment.id}" method="post" class="comment-reply-form" id="comment-reply-form-${thread.comment.id}">
				<textarea name="content" placeholder="Enter your reply to ${creator.username} here..." class="comment-reply-textarea"></textarea>
				<input type="submit" value="Reply" class="reply-form-button" name="button">
			</form>

			<textarea name="content" class="comment-reply-textarea comment-edit-textarea" id="comment-edit-submit-text-${thread.comment.id}" form="comment-edit-submit-form-${thread.comment.id}">${thread.comment.content}</textarea>

			<div class="comment-buttons">
				<c:if test="${creator eq loggedInUser}">
					<form action="/comment/${thread.comment.id}" method="post" class="own-comment-form" id="comment-delete-form-${thread.comment.id}">
						<textarea name="content" hidden="hidden"></textarea>
						<input type="submit" value="Delete" class="reply-form-button" name="button">
					</form>
					<input type="submit" value="Edit" class="reply-form-button" onclick="toggleEdit(${thread.comment.id})" id="edit-toggle-button-${thread.comment.id}">
					<form action="/comment/${thread.comment.id}" method="post" name="comment-edit-submit-form" class="comment-edit-submit-form" id="comment-edit-submit-form-${thread.comment.id}">
						<input type="submit" value="Submit" class="reply-form-button" id="edit-submit-comment-form-${thread.comment.id}" name="button">
					</form>
				</c:if>
				<input type="submit" value="Reply" onclick="toggleReplyForm(${thread.comment.id})" id="reply-form-toggle-button-${thread.comment.id}" class="reply-form-button">
			</div>
		</div>
		<commentThreads:commentThreads list="${thread.children}"/>
	</div>
</c:forEach>

<script>
	function toggleReplyForm(id) {
		var form = document.getElementById("comment-reply-form-" + id);
		var button = document.getElementById("reply-form-toggle-button-" + id);
		form.style.display = "block";
		button.style.display = "none";
	}
	function toggleEdit(id) {
		var deleteForm = document.getElementById("comment-delete-form-" + id);
		var editForm = document.getElementById("comment-edit-submit-form-" + id);
		var replyButton = document.getElementById("reply-form-toggle-button-" + id);
		var editButton = document.getElementById("edit-toggle-button-" + id);
		var editTextArea = document.getElementById("comment-edit-submit-text-" + id);
		var commentText = document.getElementById("comment-text-" + id);
		
		commentText.style.display = "none";
		editTextArea.style.display = "block";
		editForm.style.display = "inline-flex";
		deleteForm.style.display = "none";
		replyButton.style.display = "none";
		editButton.style.display = "none";
	}
</script>
