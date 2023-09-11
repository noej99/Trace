    const itemsPerPage = 10;
    let currentPage = 1;

  function fetchNews(page) {
    $.ajax({
        url: `http://195.168.9.95:3000/news.world.get?page=${page}`,
        method: "GET",
        success: function(data) {
            displayNews(data);
            currentPage = page; // Update the current page
        },
        error: function(err) {
            console.error("Error fetching data:", err);
        }
    });
}

    function formatDate(dateString) {
        const date = new Date(dateString);

        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');

        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    function displayNews(newsData) {
        const newsContainer = document.getElementById("news-container");
        newsContainer.innerHTML = ""; 

        for (const newsItem of newsData) {
            const newsItemDiv = document.createElement("div");
            newsItemDiv.className = "news-item";

            const titleElement = document.createElement("h2");
            const titleLink = document.createElement("a");
            titleLink.textContent = newsItem.title;
            titleLink.href = newsItem._id;
             titleLink.addEventListener("click", function(event) {
            event.preventDefault();
            window.open(newsItem._id, "_blank");
        });
            titleElement.appendChild(titleLink);

            const descriptionElement = document.createElement("p");
            descriptionElement.textContent = newsItem.description;

            const pubdateElement = document.createElement("p");
            const formattedPubdate = formatDate(newsItem.pubdate);
            pubdateElement.textContent = formattedPubdate;

            newsItemDiv.appendChild(titleElement);
            newsItemDiv.appendChild(descriptionElement);
            newsItemDiv.appendChild(pubdateElement);

            newsContainer.appendChild(newsItemDiv);
        }

        const paginationControls = document.getElementById("pagination-controls");
        paginationControls.innerHTML = "";

        const prevButton = document.createElement("span");
        prevButton.className = "pagination-button";
        prevButton.textContent = "이전";
        prevButton.addEventListener("click", () => {
            if (currentPage > 1) {
                currentPage--;
                window.scrollTo({ top: 0, behavior: "smooth" });
                fetchNews(currentPage);
            }
        });

        const nextButton = document.createElement("span");
        nextButton.className = "pagination-button";
        nextButton.textContent = "다음";
        nextButton.addEventListener("click", () => {
            currentPage++;
            window.scrollTo({ top: 0, behavior: "smooth" });
            fetchNews(currentPage);
        });

        paginationControls.appendChild(prevButton);
        paginationControls.appendChild(nextButton);
    }
    

   
    fetchNews(currentPage);


   