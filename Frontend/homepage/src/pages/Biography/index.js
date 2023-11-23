import HeaderAfterLogin from "../../Components/HeaderAfterLogin"
import { MantineProvider } from "@mantine/core";

function Biography(){
    return(
        <div className="biography">
            <MantineProvider>
                <HeaderAfterLogin isLoggedIn={true}/>
            </MantineProvider>
            <div className="bioBlurBackground"/>
            <div className="userBioBackground">
            </div>
            <div className="bioHead">
                <div className="bioHeadContent">
                    <div className="bioUserAvatar">
                        <img className="bioUserAvatarImg" src="/static/media/Dat.52f106a226fc942ca8f6.png"/>
                    </div>
                    <div className="bioUsernameFriend">
                        <p className="bioUsername">Nguyễn Ngọc Đạt</p>
                    </div>
                </div>
                <hr className="bioHeadSeparateLine"/>
                <div className="bioHeadNavItems">
                    <p className="bioHeadNavItem">Bài đăng</p>
                    <p className="bioHeadNavItem">Giới thiệu</p>
                    <p className="bioHeadNavItem">Bạn bè</p>
                    <p className="bioHeadNavItem">Ảnh</p>
                </div>
            </div>
            <div className="bioMainBackground">
                <div className="bioMain">
                    <div className="bioMainRight">
                        <div className="bioAddPost">
                            <img className="bioAddPostUserAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                            <div className="bioAddPostInput">
                                <p className="bioAddPostInputText">Hãy đăng gì đó</p>
                            </div>
                            <img className="bioAddPostIcon" src={require("../../images/fileIcon.png")}/>
                        </div>
                        <div className="bioRightTitle">
                            <p className="bioRightTitleText">Bài viết</p>
                        </div>
                        <div className="bioPosts">
                            <div className="bioPost">
                                <div className="bioPostHeader">
                                    <div className="bioPublisherInfo">
                                        <img className="bioPublisherAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                                        <div className="bioPublisherNameDate">
                                            <p className="bioPublisherName">Nguyễn Ngọc Đạt</p>
                                            <p className="bioPublishDate">24 tháng 3</p>
                                        </div>
                                    </div>
                                    <img className="bioThreeDotIcon" src={require("../../images/threeDotIcon.png")}/>
                                </div>
                                <div className="bioPostContent">
                                    <p className="bioPostText">Sơ đồ tư duy về mục tiêu</p>
                                    <img className="bioPostImage" src={require("../../images/mindmap1.jpg")} alt=""/>
                                </div>
                                <div className="bioPostFooter">
                                    <div className="bioPostFooterCount">
                                        <div className="bioPostLikeCount">
                                            <img className="bioPostLikeIcon" src={require("../../images/likeIcon.png")} alt=""/>
                                            <p className="bioPostLikeNum">100</p>
                                        </div>
                                        <div className="bioPostCommentCount">
                                            <img className="bioPostCommentIcon" src={require("../../images/commentIcon.png")} alt=""/>
                                            <p className="bioPostCommentNum">28</p>
                                        </div>
                                    </div>
                                    <div className="bioPostFooterButton">
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/likeButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Thích</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/commentButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Bình luận</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/shareButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Chia sẻ</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bioPost">
                                <div className="bioPostHeader">
                                    <div className="bioPublisherInfo">
                                        <img className="bioPublisherAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                                        <div className="bioPublisherNameDate">
                                            <p className="bioPublisherName">Nguyễn Ngọc Đạt</p>
                                            <p className="bioPublishDate">24 tháng 3</p>
                                        </div>
                                    </div>
                                    <img className="bioThreeDotIcon" src={require("../../images/threeDotIcon.png")}/>
                                </div>
                                <div className="bioPostContent">
                                    <p className="bioPostText">Sơ đồ tư duy về mục tiêu</p>
                                    <img className="bioPostImage" src={require("../../images/mindmap1.jpg")} alt=""/>
                                </div>
                                <div className="bioPostFooter">
                                    <div className="bioPostFooterCount">
                                        <div className="bioPostLikeCount">
                                            <img className="bioPostLikeIcon" src={require("../../images/likeIcon.png")} alt=""/>
                                            <p className="bioPostLikeNum">100</p>
                                        </div>
                                        <div className="bioPostCommentCount">
                                            <img className="bioPostCommentIcon" src={require("../../images/commentIcon.png")} alt=""/>
                                            <p className="bioPostCommentNum">28</p>
                                        </div>
                                    </div>
                                    <div className="bioPostFooterButton">
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/likeButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Thích</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/commentButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Bình luận</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/shareButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Chia sẻ</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bioPost">
                                <div className="bioPostHeader">
                                    <div className="bioPublisherInfo">
                                        <img className="bioPublisherAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                                        <div className="bioPublisherNameDate">
                                            <p className="bioPublisherName">Nguyễn Ngọc Đạt</p>
                                            <p className="bioPublishDate">24 tháng 3</p>
                                        </div>
                                    </div>
                                    <img className="bioThreeDotIcon" src={require("../../images/threeDotIcon.png")}/>
                                </div>
                                <div className="bioPostContent">
                                    <p className="bioPostText">Sơ đồ tư duy về mục tiêu</p>
                                    <img className="bioPostImage" src={require("../../images/mindmap1.jpg")} alt=""/>
                                </div>
                                <div className="bioPostFooter">
                                    <div className="bioPostFooterCount">
                                        <div className="bioPostLikeCount">
                                            <img className="bioPostLikeIcon" src={require("../../images/likeIcon.png")} alt=""/>
                                            <p className="bioPostLikeNum">100</p>
                                        </div>
                                        <div className="bioPostCommentCount">
                                            <img className="bioPostCommentIcon" src={require("../../images/commentIcon.png")} alt=""/>
                                            <p className="bioPostCommentNum">28</p>
                                        </div>
                                    </div>
                                    <div className="bioPostFooterButton">
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/likeButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Thích</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/commentButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Bình luận</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/shareButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Chia sẻ</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bioPost">
                                <div className="bioPostHeader">
                                    <div className="bioPublisherInfo">
                                        <img className="bioPublisherAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                                        <div className="bioPublisherNameDate">
                                            <p className="bioPublisherName">Nguyễn Ngọc Đạt</p>
                                            <p className="bioPublishDate">24 tháng 3</p>
                                        </div>
                                    </div>
                                    <img className="bioThreeDotIcon" src={require("../../images/threeDotIcon.png")}/>
                                </div>
                                <div className="bioPostContent">
                                    <p className="bioPostText">Sơ đồ tư duy về mục tiêu</p>
                                    <img className="bioPostImage" src={require("../../images/mindmap1.jpg")} alt=""/>
                                </div>
                                <div className="bioPostFooter">
                                    <div className="bioPostFooterCount">
                                        <div className="bioPostLikeCount">
                                            <img className="bioPostLikeIcon" src={require("../../images/likeIcon.png")} alt=""/>
                                            <p className="bioPostLikeNum">100</p>
                                        </div>
                                        <div className="bioPostCommentCount">
                                            <img className="bioPostCommentIcon" src={require("../../images/commentIcon.png")} alt=""/>
                                            <p className="bioPostCommentNum">28</p>
                                        </div>
                                    </div>
                                    <div className="bioPostFooterButton">
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/likeButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Thích</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/commentButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Bình luận</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/shareButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Chia sẻ</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bioPost">
                                <div className="bioPostHeader">
                                    <div className="bioPublisherInfo">
                                        <img className="bioPublisherAva" src="/static/media/Dat.52f106a226fc942ca8f6.png" alt=""/>
                                        <div className="bioPublisherNameDate">
                                            <p className="bioPublisherName">Nguyễn Ngọc Đạt</p>
                                            <p className="bioPublishDate">24 tháng 3</p>
                                        </div>
                                    </div>
                                    <img className="bioThreeDotIcon" src={require("../../images/threeDotIcon.png")}/>
                                </div>
                                <div className="bioPostContent">
                                    <p className="bioPostText">Sơ đồ tư duy về mục tiêu</p>
                                    <img className="bioPostImage" src={require("../../images/mindmap1.jpg")} alt=""/>
                                </div>
                                <div className="bioPostFooter">
                                    <div className="bioPostFooterCount">
                                        <div className="bioPostLikeCount">
                                            <img className="bioPostLikeIcon" src={require("../../images/likeIcon.png")} alt=""/>
                                            <p className="bioPostLikeNum">100</p>
                                        </div>
                                        <div className="bioPostCommentCount">
                                            <img className="bioPostCommentIcon" src={require("../../images/commentIcon.png")} alt=""/>
                                            <p className="bioPostCommentNum">28</p>
                                        </div>
                                    </div>
                                    <div className="bioPostFooterButton">
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/likeButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Thích</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/commentButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Bình luận</p>
                                        </div>
                                        <div className="bioPostButton">
                                            <img className="bioPostButtonIcon" src={require("../../images/shareButtonIcon.png")} alt=""/>
                                            <p className="bioPostButtonText">Chia sẻ</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Biography