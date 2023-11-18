import React from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserProducts from './UserProducts';
class SidebarContent extends React.Component {
    render() {
        return (
            <>
                <div className='SidebarContent'>
                    <div className='ContentBox'>
                        <div className='Content'>
                            <svg className='IconContent' width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M9.16046 1.29732C9.55452 0.68838 10.4455 0.68838 10.8395 1.29732L11.7217 2.66053C11.9789 3.05794 12.4795 3.22058 12.9211 3.05024L14.4361 2.46591C15.1128 2.2049 15.8336 2.72859 15.7945 3.45285L15.7069 5.07425C15.6814 5.54692 15.9908 5.97274 16.4482 6.09452L18.0173 6.51227C18.7182 6.69888 18.9935 7.54622 18.5362 8.10917L17.5123 9.36944C17.2138 9.73683 17.2138 10.2632 17.5123 10.6306L18.5362 11.8908C18.9935 12.4538 18.7182 13.3011 18.0173 13.4877L16.4482 13.9055C15.9908 14.0273 15.6814 14.4531 15.7069 14.9257L15.7945 16.5471C15.8336 17.2714 15.1128 17.7951 14.4361 17.5341L12.9211 16.9498C12.4795 16.7794 11.9789 16.9421 11.7217 17.3395L10.8395 18.7027C10.4455 19.3116 9.55452 19.3116 9.16046 18.7027L8.27828 17.3395C8.0211 16.9421 7.52052 16.7794 7.07887 16.9498L5.56389 17.5341C4.88716 17.7951 4.16637 17.2714 4.20549 16.5471L4.29306 14.9257C4.31859 14.4531 4.00922 14.0273 3.55179 13.9055L1.98269 13.4877C1.28178 13.3011 1.00646 12.4538 1.46383 11.8908L2.48771 10.6306C2.78619 10.2632 2.78619 9.73683 2.48771 9.36944L1.46382 8.10917C1.00646 7.54622 1.28178 6.69888 1.98269 6.51227L3.55179 6.09452C4.00922 5.97274 4.31859 5.54692 4.29306 5.07425L4.20549 3.45285C4.16637 2.72859 4.88716 2.2049 5.56389 2.46591L7.07887 3.05024C7.52052 3.22058 8.0211 3.05794 8.27828 2.66053L9.16046 1.29732Z" fill="#0ECC8D" />
                                <path d="M4.462 12V8.088H5.368L6.388 10.032L6.772 10.896H6.796C6.78 10.688 6.758 10.456 6.73 10.2C6.702 9.944 6.688 9.7 6.688 9.468V8.088H7.528V12H6.622L5.602 10.05L5.218 9.198H5.194C5.214 9.414 5.236 9.646 5.26 9.894C5.288 10.142 5.302 10.382 5.302 10.614V12H4.462ZM9.71223 12.072C9.42823 12.072 9.17223 12.01 8.94423 11.886C8.71623 11.762 8.53623 11.584 8.40423 11.352C8.27223 11.12 8.20623 10.84 8.20623 10.512C8.20623 10.188 8.27223 9.91 8.40423 9.678C8.54023 9.446 8.71623 9.268 8.93223 9.144C9.14823 9.016 9.37423 8.952 9.61023 8.952C9.89423 8.952 10.1282 9.016 10.3122 9.144C10.5002 9.268 10.6402 9.438 10.7322 9.654C10.8282 9.866 10.8762 10.108 10.8762 10.38C10.8762 10.456 10.8722 10.532 10.8642 10.608C10.8562 10.68 10.8482 10.734 10.8402 10.77H9.05823C9.09823 10.986 9.18823 11.146 9.32823 11.25C9.46823 11.35 9.63623 11.4 9.83223 11.4C10.0442 11.4 10.2582 11.334 10.4742 11.202L10.7682 11.736C10.6162 11.84 10.4462 11.922 10.2582 11.982C10.0702 12.042 9.88823 12.072 9.71223 12.072ZM9.05223 10.188H10.1262C10.1262 10.024 10.0862 9.89 10.0062 9.786C9.93023 9.678 9.80423 9.624 9.62823 9.624C9.49223 9.624 9.37023 9.672 9.26223 9.768C9.15423 9.86 9.08423 10 9.05223 10.188ZM11.9485 12L11.2045 9.024H12.0805L12.3685 10.404C12.3925 10.552 12.4145 10.698 12.4345 10.842C12.4545 10.986 12.4765 11.134 12.5005 11.286H12.5245C12.5525 11.134 12.5785 10.984 12.6025 10.836C12.6305 10.688 12.6625 10.544 12.6985 10.404L13.0285 9.024H13.7905L14.1265 10.404C14.1625 10.552 14.1945 10.698 14.2225 10.842C14.2545 10.986 14.2845 11.134 14.3125 11.286H14.3365C14.3645 11.134 14.3865 10.986 14.4025 10.842C14.4225 10.698 14.4465 10.552 14.4745 10.404L14.7565 9.024H15.5725L14.8585 12H13.8265L13.5565 10.812C13.5285 10.672 13.5005 10.532 13.4725 10.392C13.4485 10.252 13.4225 10.102 13.3945 9.942H13.3705C13.3425 10.102 13.3165 10.252 13.2925 10.392C13.2725 10.532 13.2485 10.672 13.2205 10.812L12.9565 12H11.9485Z" fill="white" />
                            </svg>

                            <div className='ContentText'>Gần đây</div>
                        </div>
                        <div className='Content'>
                            <svg className='IconContent' width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M6.72134 14.4503L9 16.8905C9.22611 17.1259 9.64018 16.9547 9.64018 16.6338V14.4718L11.4491 14.4717C12.4357 14.4717 13.2373 13.6372 13.2373 12.6102L13.2375 4.86147C13.2375 3.83447 12.4358 3 11.4492 3H1.78827C0.801653 3 0 3.83447 0 4.86147V12.5888C0 13.6158 0.801653 14.4503 1.78827 14.4503L6.72134 14.4503ZM4.11094 7.42909L5.71424 7.30073L6.33093 5.73882C6.45424 5.43927 6.84481 5.43927 6.94763 5.73882L7.56432 7.30073L9.16763 7.42909C9.4759 7.45043 9.59936 7.83566 9.35259 8.04951L8.1192 9.1407L8.50979 10.7668C8.59195 11.0664 8.26317 11.3231 7.99591 11.1519L6.61871 10.2746L5.24151 11.1519C4.97425 11.3231 4.66597 11.0663 4.72763 10.7668L5.11822 9.1407L3.88483 8.04951C3.67922 7.85698 3.80252 7.45056 4.11094 7.42909ZM15.5602 6.10254C15.108 6.10254 14.7381 5.71747 14.7381 5.24675C14.7381 4.77602 15.108 4.39096 15.5602 4.39096H19.1779C19.6301 4.39096 20 4.77602 20 5.24675C20 5.71747 19.6301 6.10254 19.1779 6.10254H15.5602ZM14.7585 8.64877C14.7585 8.17805 15.1285 7.79298 15.5807 7.79298H18.2529C18.7051 7.79298 19.075 8.17805 19.075 8.64877C19.075 9.1195 18.7051 9.50456 18.2529 9.50456H15.5602C15.108 9.50456 14.7585 9.1195 14.7585 8.64877ZM14.7585 12.0508C14.7585 11.5801 15.1285 11.195 15.5807 11.195H17.2046C17.6568 11.195 18.0267 11.5801 18.0267 12.0508C18.0267 12.5215 17.6568 12.9066 17.2046 12.9066H15.5601C15.1079 12.9066 14.7585 12.5215 14.7585 12.0508Z" fill="#EEA956" />
                            </svg>

                            <div className='ContentText'>Nổi bật</div>
                        </div>
                        <div className='Content'>
                            <svg className='IconContent' width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M13 5C13 2.79066 11.2089 1 9 1C6.79109 1 5 2.79066 5 5C5 7.20934 6.79109 9 9 9C11.2089 9 13 7.20934 13 5Z" fill="#FF6934" />
                                <path d="M12 9C11.2357 9.5784 10.0266 10 9 10C7.95345 10 6.7718 9.59874 6 9C1.10197 10.179 0.910523 14.2341 1.0085 17.979C1.0247 18.5984 1.3724 19.0001 2 19.0001L11 19V16.0001C11 14.9814 11.307 14.0001 13 14.0001L16.5 14C16.5 11 14.5 9 12 9Z" fill="#FF6934" />
                                <path d="M13 17H19M19 17L17.5 15.5M19 17L17.5 18.5" stroke="#FF6934" stroke-linecap="round" stroke-linejoin="round" />
                            </svg>

                            <div className='ContentText'>Đang theo dõi</div>
                            {this.props.isLoggedIn &&
                                <div className='FollowingNum'>{this.props.following}</div>
                            }
                        </div>
                        <UserProducts isLoggedIn={this.props.isLoggedIn} />
                    </div>
                </div>

            </>
        );
    }
}

export default SidebarContent;