import React from 'react'
import ReactPaginate from 'react-paginate';
class Pagenumber extends React.Component {
    render() {
        const handlePageClick = () => {

        }
        return (
            <>

                {/* <div className='Frame15'>
                    <div className='Frame40'>
                        <div className='Frame40Text'>←
                        </div>
                    </div>
                    <div className='Pagenumber'><div className='PagenumberText'>1
                    </div></div>
                    <div className='Pagenumber'><div className='PagenumberText'>2
                    </div></div>
                    <div className='Pagenumber'><div className='PagenumberText'>3
                    </div></div>
                    <div className='More'>...</div>
                    <div className='Pagenumber'><div className='PagenumberText'>27
                    </div></div>
                    <div className='Pagenumber'><div className='PagenumberText'>28
                    </div></div>
                    <div className='Pagenumber'><div className='PagenumberText'>29
                    </div></div>
                    <div className='Pagenumber'><div className='Frame40Text'>→
                    </div>
                    </div>
                </div> */}
                <ReactPaginate
                    breakLabel="..."
                    nextLabel="→"
                    onPageChange={handlePageClick}
                    pageRangeDisplayed={3}
                    pageCount={1231}
                    previousLabel="←"
                    pageClassName='page-item'
                    pageLinkClassName='page-link'
                    previousClassName='page-item'
                    previousLinkClassName='jpage-link'
                    nextClassName='page-item'
                    nextLinkClassName='page-link'
                    breakClassName='page-item'
                    breakLinkClassName='page-link'
                    containerClassName='pagination'
                    activeClassName='active'
                />
            </>
        );
    }
}

export default Pagenumber;