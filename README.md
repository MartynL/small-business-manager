# Small-Business-Manager
A business-generic project that allows small businesses like home bakers or home hairdressers to create price lists and packages. Input orders and track minimum finances. View analytics on their order history so they can make informed decisions / projections based on their selling trends.

# Aims:
Proof of concept: provide a small standalone app that can be installed on a deskptop / laptop to allow individuals - or a few people - to track their small business events. A dashboard on entry will show an overall view of the small business activity. Facilities to manage both orders and items for sale will be provided through a minimal, intuitive interface.

# PoC use cases
  - items for sale, produced by the business:
    - Allows a Business user to enter the items they want to sell and price accordingly.
    - Allows a Business user control over the management of these items (create, update, delete)
  
  - Packages:
    - Multiple items contained as a single unit with a single price.
    - Allows a Business user control over the management of these items (create, update, delete) 
    - Can have state enabled / disabled for greater control. If disabled, then package is removed from price lists and cannot be added to new orders. Keeps things neater if the       business is seasonal and packages are only available for a short time. Also useful if there are more than one business users.* 
  
  - Price Lists:
    - Can be constructed from items or packages
    - Allows a Business user control over the management of these items (create, update, delete)
    - Can have state enabled / disabled for greater control. If disabled, then orders cannot be created from that price list. Keeps things neater if the business is seasonal and       wants to keep previous price lists but not have them accessible during certain months. Also useful if there are more than one business users.*
        
  - Orders:
    - Allow user to input orders from a particular price list
    - Order state to be tracked: Order Created, Order Updated, Order Completed, Order Payment Taken, Order Closed (this could expand out but good enough for PoC)
    - Facility to add discounts to an order, either through direct application by user or by setting up code systems for promotional use.
    
  - Dashboard:
    - provides an overview of**:
      - Orders:
        - visual displays of daily, weekly, monthly, yearly order creation
        - on time orders vs overshot
      - Price lists:
        - List rankings for items (most commonly ordered item)
      - Quick glance at business activity
        - Most recent orders
        - new Price Lists created / disabled / deleted
        
# Technology
Poc will be built in a layered monolithic architecture with Java 11, using spring boot 2.4.0 and Thymeleaf for the view layer. 

###### * Haven't decided on whether or not I want to add these features yet.
###### ** I believe this will become more clear through user feedback on what they actually want displayed. Might look at giving the user control over that as well i.e turn on / off      panels and allowing them to choose what's most useful for them to know up front.
