# Intelli-Edu
## Server
- docker pull ghcr.io/khoanguyenducanh/intelli-edu:snapshot
- docker run --network host -v {Path của folder Frontend}:/Frontend ghcr.io/khoanguyenducanh/intelli-edu:snapshot
## API
/api/v1

### Auth
- POST /auth/authenticate
{
	email
	password
}
- POST /auth/register
{
	email
	username
	password
	confirmPassword
}
- GET /auth/activate/{token}
### Account
- GET  /account
### Comment
- POST /comment/{postId}
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	content: 
}
- PUT /comment
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	content: 
}
- DELETE /comment/{id}
### Document
- GET /document?search={tên}
- GET /document/{id}
- POST /document
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	tilte: ,
	subject: ,
	content: 
}
- PUT /document
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	tilte: ,
	subject: ,
	content: 
}
- DELETE /document/{id}
### Event
- GET /event
- POST /event
{
	id: ,
	name: ,
	deadline: ,
	urgent: ,
	important: ,
	eventType: ,
	description: 
}
- PUT /event
{
	id: ,
	name: ,
	deadline: ,
	urgent: ,
	important: ,
	eventType: ,
	description: 
}
- DELETE /event/{id}
### Mind Map
- GET /mindmap?search={tên}
- GET /mindmap/{id}
- POST /mindmap
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	tilte: ,
	subject: ,
	content: 
}
- PUT /mindmap
{
	postDto: {
		id: ,
		createdAt: ,
		lastOpened: ,
		upvote: ,
		downvote: ,
	},
	tilte: ,
	subject: ,
	content: 
}
- DELETE /mindmap/{id}
### Post
- PUT /post/upvote/{id}
- PUT /post/downvote/{id}