# Intelli-Edu
## Server
- docker pull ghcr.io/khoanguyenducanh/intelli-edu:snapshot
- docker run --network host -v {Path của folder Frontend}:/Frontend ghcr.io/khoanguyenducanh/intelli-edu:snapshot
## API
/api/v1
## postDto
### Account
- GET  /account
### Comment
- POST /comment/{postId}
- PUT /comment
- DELETE /comment/{id}
### Document
- GET /document?search={}
	- Tìm document bằng tên
- GET /document/{id}
	- Lấy document bằng id
- POST /document
- PUT /document
- DELETE /document/{id}
### Event
- GET /event
- POST /event
- PUT /event
- DELETE /event/{id}
### Mind Map
- GET /mindmap?search={}
	- Tìm mindmap bằng tên
- GET /mindmap/{id}
	- Lấy mindmap bằng id
- POST /mindmap
- PUT /mindmap
- DELETE /mindmap/{id}
### Post
- PUT /post/upvote/{id}
- PUT /post/downvote/{id}