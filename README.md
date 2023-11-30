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
 
### Potential Issues in the design
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

### How to use it?
Simply clone the project and install the required libraries. If you have any issues or want to make any improvements.
Feel free to send a pull request.

