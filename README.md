# Shop Management App

### What will the project do?

This project is designed to help users manage their shops by allowing them to add new items, update their quantities,
upload their pictures, and track sales. The project also includes features for setting inventory limits and sending 
notifications when inventory levels fall below the set limit. 

 You can begin adding items to your inventory by providing a name, quantity, picture, and type for each item. You can
 also update the quantities of existing items and track sales by subtracting the quantity sold from the total inventory.
You can set an inventory limit for each item by going to the inventory tab and selecting the item you want to set a 
limit for. If the inventory level falls below the set limit, the system will send a notification to the user. It can
also generate summary of sales so users can do analysis on their business



### Who will use it?

Potential users of this project could include 


- small business owners
- store managers
- individuals who sell goods through a physical or online shop
- Any seller who does not have their own inventory managing systems. 


This project would be 
particularly useful for any individual or businesses that have a physical storefront and need to track inventory levels and sales,as well
as businesses that sell goods online and need to keep track of inventory and shipping.
 
### Why I am Interested in this project?

I am interested in this project because it allows me to apply my knowledge of business and computer science to create a 
valuable tool for small business owners and entrepreneurs. As a BUCS student, I am particularly excited about the 
opportunity to combine my interests in technology and business to create a solution that can help businesses run 
more efficiently and effectively. Additionally, my dad's experience as a business owner has given me a firsthand
understanding of the challenges that businesses face when it comes to managing inventory and tracking sales. 
This has motivated me to choose this as my project.

### User Stories

- As a user, I want to be able to use buttons to add new items to my shop inventory with their quantities
- As a user, I want to be able to use buttons and fields to record sales and adjust inventory accordingly, so that I can
keep track of the items that have been sold.
- As a user, I want to be able to set an inventory limit for each item and receive notifications when the inventory
falls below the limit, so that I can reorder items before I run out of stock.
- As a user, I want to be able to generate reports of my inventory, so that I can analyze my business
performance.
- As a user, I want to save my items in Inventory.
- As a user, I want to load my Inventory items from a file

### Phase 4: task 2
Wed Apr 12 02:00:51 PDT 2023 
Bought new item for inventory 
Wed Apr 12 02:01:16 PDT 2023 
Bought new item for inventory 
Wed Apr 12 02:01:27 PDT 2023
Removed item from inventory 
Wed Apr 12 02:01:51 PDT 2023 
Creditor added successfully  
Wed Apr 12 02:01:51 PDT 2023 
Performed a credit sale   
Wed Apr 12 02:02:03 PDT 2023 
Performed Cash Sales 
Wed Apr 12 02:02:20 PDT 2023 
Recorded a credit return 
Wed Apr 12 02:03:20 PDT 2023 
Performed a cash return 
Wed Apr 12 02:03:38 PDT 2023 
Payment received by the creditor 
Wed Apr 12 02:03:51 PDT 2023 
Creditor removed successfully 

### Phase 4: task 3
Upon reviewing the UML diagram, I noticed that the Bank and Inventory classes have a significant amount of coupling.
This can lead to problems with maintainability and flexibility as changes in one class might inadvertently affect 
the other. To mitigate this issue, I would adopt a more suitable design pattern, such as the Observer pattern,
which would allow these classes to communicate more efficiently and loosely coupled.

Furthermore, I have identified several methods in the code that are similar in functionality.
This redundancy can be streamlined by creating a single common method that can be called with different parameters
to achieve the desired outcome. This would not only improve code readability but also make it easier to maintain and
extend in the future. Additionally, while building the UI, I noticed that frames are being created in every UI-related
method, which hampers code readability. To address this, I would refactor the UI code to create and manage frames
in a centralized manner, making the code more modular and easier to read.